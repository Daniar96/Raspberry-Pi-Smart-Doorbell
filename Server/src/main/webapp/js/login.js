let username_field = document.getElementById("username");
let password_field = document.getElementById("password");
let invalid_credentials = document.getElementById('invalid-message');

function login() {
    event.preventDefault();
    console.log()
    $.ajax({
        url: `http://localhost:8080/Server_war/api/login`,
        type: "POST",
        async: true,
        contentType: "application/json",
        data: `{"username": "${username_field.value}", 
                "password": "${password_field.value}"}`,
        success: function (id) {
            location.href = 'dashboard.html';
        },
        error: function (response) {
            if (response.status === 403) {
                handleInvalidCredentials();
            }
        }
    });
}

function logout() {
    window.location.href = 'login.html';
}

function handleInvalidCredentials() {
    invalid_credentials.innerHTML = "Invalid credentials!";
    username_field.value = "";
    password_field.value = "";
    username_field.classList.add("border-danger");
    password_field.classList.add("border-danger");
}

function clearInvalidCredentials() {
    invalid_credentials.innerHTML = "";
    username_field.classList.remove("border-danger");
    password_field.classList.remove("border-danger");
}