const API_URL = 'http://localhost:8080/api/pets';

document.getElementById('adoptBtn').addEventListener('click', adoptPet);
document.getElementById('refreshBtn').addEventListener('click', listPets);

async function adoptPet() {
    const pet = {
        name: document.getElementById('name').value,
        species: document.getElementById('species').value,
        hungerLevel: parseInt(document.getElementById('hungerLevel').value),
        happinessLevel: parseInt(document.getElementById('happinessLevel').value)
    };

    const res = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pet)
    });

    if (res.ok) {
        await res.json();
        listPets();
    } else {
        const text = await res.text();
        alert('Error: ' + text);
    }
}

async function listPets() {
    const res = await fetch(API_URL);
    const pets = await res.json();
    const container = document.getElementById('pets');
    container.innerHTML = '';

    pets.forEach(pet => {
        const div = document.createElement('div');
        div.className = 'pet';
        div.innerHTML = `
          <strong>ID: ${pet.id} ${pet.name} (${pet.species})</strong><br>
          Hunger: ${pet.hungerLevel}, Happiness: ${pet.happinessLevel}<br>
          <button onclick="feedPet(${pet.id})">Feed</button>
          <button onclick="playPet(${pet.id})">Play</button>
          <button onclick="releasePet(${pet.id})">Release</button>
        `;
        container.appendChild(div);
    });
}

async function feedPet(id) {
    const amount = 5;
    await fetch(`${API_URL}/${id}/feed?amount=${amount}`, { method: 'PUT' });
    listPets();
}

async function playPet(id) {
    const amount = 5;
    await fetch(`${API_URL}/${id}/play?amount=${amount}`, { method: 'PUT' });
    listPets();
}

async function releasePet(id) {
    await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    listPets();
}

listPets();
