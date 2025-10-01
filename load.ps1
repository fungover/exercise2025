$client = [System.Net.Http.HttpClient]::new()
$sw = [System.Diagnostics.Stopwatch]::StartNew()

while ($true) {
    $client.GetAsync("http://localhost:8080/api/hello?name=Test") | Out-Null
    $client.GetAsync("http://localhost:8080/api/hello/guestbook") | Out-Null

    if ($sw.ElapsedMilliseconds -gt 1000) {
        Write-Host "Still running... $(Get-Date)"
        $sw.Restart()
    }
}
