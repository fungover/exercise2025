# ===========================
# A powershell test script for my REST API (look into the plugin later)
# Runs with:  .\test-pets.ps1
# Displays with color in terminal
# ===========================

$ErrorActionPreference = 'Stop'
$baseUrl = "http://localhost:8080/api/pets"
$pass = 0; $fail = 0

function Show-Step($title)
{
    Write-Host ""
    Write-Host "== $title ==" -ForegroundColor Cyan
}

function Show-Result($ok, $msgOk, $msgFail, $body = $null)
{
    if ($ok)
    {
        $script:pass++
        Write-Host "  ✅ $msgOk" -ForegroundColor Green
        if ($body)
        {
            Write-Host "     " $body
        }
    }
    else
    {
        $script:fail++
        Write-Host "  ❌ $msgFail" -ForegroundColor Red
        if ($body)
        {
            Write-Host "     " $body
        }
    }
}

# Invoke-WebRequest wrapper that returns Status + Body even in codegroup 4xx/5xx in PS 5.1
function Invoke-Api
{
    param(
        [Parameter(Mandatory)][ValidateSet('GET', 'POST', 'PUT', 'DELETE')] [string]$Method,
        [Parameter(Mandatory)][string]$Url,
        [string]$BodyJson
    )

    $headers = @{ "Content-Type" = "application/json" }
    $params = @{ Method = $Method; Uri = $Url; Headers = $headers }
    if ($BodyJson)
    {
        $params.Body = $BodyJson
    }

    if ($PSVersionTable.PSVersion.Major -ge 7)
    {
        # Always decode body like UTF-8 text
        $resp = Invoke-WebRequest @params -SkipHttpErrorCheck
        $status = [int]$resp.StatusCode

        if ($resp.RawContentStream)
        {
            $reader = New-Object System.IO.StreamReader($resp.RawContentStream, [System.Text.Encoding]::UTF8, $true, 1024, $true)
            $content = $reader.ReadToEnd()
            $resp.RawContentStream.Position = 0
        }
        elseif ($resp.Content -is [byte[]])
        {
            $content = [System.Text.Encoding]::UTF8.GetString($resp.Content)
        }
        else
        {
            $content = [string]$resp.Content
        }
    }
    else
    {
        # fallback
        try
        {
            $resp = Invoke-WebRequest @params -UseBasicParsing
            $status = if ($resp.StatusCode)
            {
                [int]$resp.StatusCode
            }
            else
            {
                200
            }
            $content = $resp.Content
        }
        catch
        {
            $errResp = $_.Exception.Response
            if ($errResp -ne $null)
            {
                $status = [int]$errResp.StatusCode
                $reader = New-Object System.IO.StreamReader($errResp.GetResponseStream())
                $content = $reader.ReadToEnd()
            }
            else
            {
                $status = 0
                $content = $_.Exception.Message
            }
        }
    }

    [PSCustomObject]@{ Status = $status; Body = $content }
}

# 0) POST - validation error (400)
Show-Step "0) POST /api/pets (validation error -> 400)"
$badBody = '{"name":"","species":"","hungerLevel":150,"happiness":-1}'
$r = Invoke-Api -Method POST -Url $baseUrl -BodyJson $badBody
Show-Result ($r.Status -eq 400 -and $r.Body -like '*"name"*') "400 Bad Request (validation) OK" "Expected 400 with validation errors, got $( $r.Status )" $r.Body

# 1) POST – adopt
Show-Step "1) POST /api/pets (adopt)"
$reqBody = '{"name":"Polly","species":"dog","hungerLevel":50,"happiness":59}'
$r = Invoke-Api -Method POST -Url $baseUrl -BodyJson $reqBody
$ok = ($r.Status -eq 201)
Show-Result $ok "201 Created" "Expected 201, got $( $r.Status )" $r.Body
if (-not $ok)
{
    Write-Host "Abort the rest of tests." -ForegroundColor Yellow; goto Summary
}

# extract id
try
{
    $id = ($r.Body | ConvertFrom-Json).id
}
catch
{
    $id = $null
}
if (-not $id)
{
    Show-Result $false "—" "Could not extract id from POST-request"; goto Summary
}

# 2) GET – list
Show-Step "2) GET /api/pets (list)"
$r = Invoke-Api -Method GET -Url $baseUrl
Show-Result ($r.Status -eq 200) "200 OK" "Expected 200, got $( $r.Status )" $r.Body

# 3) GET – get one pet
Show-Step "3) GET /api/pets/$id"
$r = Invoke-Api -Method GET -Url "$baseUrl/$id"
Show-Result ($r.Status -eq 200) "200 OK" "Expected 200, got $( $r.Status )" $r.Body

# 4) PUT – play (204)
Show-Step "4) PUT /api/pets/$id/play (happiness +10)"
$r = Invoke-Api -Method PUT -Url "$baseUrl/$id/play"
Show-Result ($r.Status -eq 204) "204 No Content" "Expected 204, got $( $r.Status )"

# 5) PUT – feed (204)
Show-Step "5) PUT /api/pets/$id/feed (hunger -10)"
$r = Invoke-Api -Method PUT -Url "$baseUrl/$id/feed"
Show-Result ($r.Status -eq 204) "204 No Content" "Expected 204, got $( $r.Status )"

# 6) GET – Check updated pet
Show-Step "6) GET /api/pets/$id (verify changes)"
$r = Invoke-Api -Method GET -Url "$baseUrl/$id"
$ok = ($r.Status -eq 200)
Show-Result $ok "200 OK" "Expected 200, got $( $r.Status )" $r.Body

# 7) DELETE – Release (204)
Show-Step "7) DELETE /api/pets/$id (release)"
$r = Invoke-Api -Method DELETE -Url "$baseUrl/$id"
Show-Result ($r.Status -eq 204) "204 No Content" "Expected 204, got $( $r.Status )"

# 8) GET – List again (Should be empty or without $id)
Show-Step "8) GET /api/pets (should not contain id $id)"
$r = Invoke-Api -Method GET -Url $baseUrl
$containsId = $r.Body -like "*`"id`": $id*"
Show-Result (-not $containsId -and $r.Status -eq 200) "200 OK, id $id gone" "Expected 200 and list without id $id, got $( $r.Status )" $r.Body

# 9) GET -404 (after delete)
Show-Step "9) GET /api/pets/$id (should 404)"
$r = Invoke-Api -Method GET -Url "$baseUrl/$id"
Show-Result ($r.Status -eq 404 -and $r.Body -like '*"title"*') "404 JSON OK" "Expected 404 with JSON body, got $( $r.Status )" $r.Body

Write-Host ""
if ($fail -eq 0)
{
    Write-Host "ALL TESTS PASSED ($pass ok)" -ForegroundColor Green
}
else
{
    Write-Host "SOME TESTS FAILED ($pass ok, $fail fail)" -ForegroundColor Red
}
