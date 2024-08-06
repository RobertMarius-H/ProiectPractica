document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('form-container').style.display = 'none';
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

