document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('register-form').style.display = 'none'; // Ascunde formularul de înregistrare inițial
});

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
        confirmPassword: form.confirmPassword.value
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
