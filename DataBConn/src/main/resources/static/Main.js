document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('register-form').style.display = 'none'; // Ascunde formularul de înregistrare inițial
});

function showInsertForm() {
    document.getElementById('form-container').style.display = 'block';
    document.getElementById('insert-form').style.display = 'block';
    document.getElementById('update-form').style.display = 'none';
    document.getElementById('delete-form').style.display = 'none';
}

function showUpdateForm() {
    document.getElementById('form-container').style.display = 'block';
    document.getElementById('insert-form').style.display = 'none';
    document.getElementById('update-form').style.display = 'block';
    document.getElementById('delete-form').style.display = 'none';
}

function showDeleteForm() {
    document.getElementById('form-container').style.display = 'block';
    document.getElementById('insert-form').style.display = 'none';
    document.getElementById('update-form').style.display = 'none';
    document.getElementById('delete-form').style.display = 'block';
}

function submitInsertForm() {
    const form = document.getElementById('insertUserForm');
    const data = {
        nume: form.nume.value,
        prenume: form.prenume.value,
        anNastere: parseInt(form.anNastere.value),
        idOcupatie: parseInt(form.idOcupatie.value),
        idOrasDomiciliu: parseInt(form.idOrasDomiciliu.value)
    };

    fetch('/api/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
            document.getElementById('form-container').style.display = 'none';
        })
        .catch(error => console.error('Error:', error));
}

function submitUpdateForm() {
    const form = document.getElementById('updateUserForm');
    const data = {
        idPers: parseInt(form.idPers.value),
        nume: form.nume.value,
        prenume: form.prenume.value,
        anNastere: parseInt(form.anNastere.value),
        idOcupatie: parseInt(form.idOcupatie.value),
        idOrasDomiciliu: parseInt(form.idOrasDomiciliu.value)
    };

    fetch('/api/update', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
            document.getElementById('form-container').style.display = 'none';
        })
        .catch(error => console.error('Error:', error));
}

function submitDeleteForm() {
    const id = document.getElementById('deleteId').value;

    fetch(`/api/delete?id=${id}`, {
        method: 'DELETE'
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
            document.getElementById('form-container').style.display = 'none';
        })
        .catch(error => console.error('Error:', error));
}

function showRegisterForm() {
    document.getElementById('register-form').style.display = 'block'; // Afișează formularul de înregistrare
    document.getElementById('loginForm').style.display = 'none'; // Ascunde formularul de login
}

function submitRegisterForm() {
    const form = document.getElementById('registerUserForm');
    const data = {
        username: form.username.value,
        email: form.email.value,
        password: form.password.value,
    };

    fetch('/api/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
            document.getElementById('register-form').style.display = 'none';
            document.getElementById('loginForm').style.display = 'block'; // Reafișează formularul de login
        })
        .catch(error => console.error('Error:', error));
}

function returnToLogin() {
    document.getElementById('register-form').style.display = 'none'; // Ascunde formularul de înregistrare
    document.getElementById('loginForm').style.display = 'block'; // Reafișează formularul de login
}

function submitLoginForm() {
    // Obține referințe la elementele formularului folosind ID-urile
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    // Verifică dacă username și password au valori
    if (!username || !password) {
        alert('Please enter both username and password.');
        return;
    }

    // Crează obiectul cu datele pentru request
    const data = {
        username: username,
        password: password
    };

    // Trimite request POST către API-ul de login
    fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                return response.text(); // Sau `response.json()` dacă API-ul returnează JSON
            } else {
                return response.text().then(text => {
                    throw new Error(text);
                });
            }
        })
        .then(data => {
            // Afișează mesajul de succes și redirecționează utilizatorul
            alert(data);
            window.location.href = '/users-view'; // Redirecționează la pagina principală
        })
        .catch(error => {
            // Afișează eroarea
            console.error('Error:', error.message);
            alert('Error: ' + error.message);
        });
}


