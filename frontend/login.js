
const API_URL = "http://localhost:8080/users";

function login() {
    const inputEmail = sanitize(document.getElementById("email").value.trim());
    const inputPassword = sanitize(document.getElementById("password").value.trim());

    if (!inputEmail || !inputPassword) {
        alert("Preencha todos os campos");
        return;
    }

    fetch(`${API_URL}/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: inputEmail,
            password: inputPassword
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Credenciais inválidas");
        }
        return response.json();
    })
    .then(user => {
        localStorage.setItem("user", JSON.stringify(user));
        window.location.href = "index.html";
    })
    .catch(error => {
        console.error("Erro ao fazer login:", error);
        alert("Erro ao fazer login. Tente novamente.");
    });
}

function cadastro() {
    const inputName = sanitize(document.getElementById("name").value.trim());
    const inputEmail = sanitize(document.getElementById("Email").value.trim());
    const inputPassword = sanitize(document.getElementById("password").value.trim());

    if (!inputName || !inputEmail || !inputPassword) {
        alert("Preencha todos os campos");
        return;
    }

    const user = {
        name: inputName,
        email: inputEmail,
        password: inputPassword
    }

    fetch(`${API_URL}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao cadastrar usuário");
        }
        return response.json();
    })
    .then(user => {
        window.location.href = "login.html";
    })
    .catch(error => {
        console.error("Erro ao cadastrar usuário:", error);
        alert("Erro ao cadastrar usuário. Tente novamente.");
    });
}


function sanitize(input) {
    const div = document.createElement("div");
    div.textContent = input;
    return div.innerHTML;
}

