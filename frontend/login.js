
const API_URL = "https://expense-control-project.onrender.com/users";

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

    // Regex de validação
    const nameRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ ]{3,40}$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;

    // Verifica campos obrigatórios
    if (!inputName || !inputEmail || !inputPassword) {
        alert("Preencha todos os campos.");
        return;
    }

    // Valida o nome
    if (!nameRegex.test(inputName)) {
        alert("Nome inválido! Use apenas letras e entre 3 e 40 caracteres.");
        return;
    }

    // Valida o e-mail
    if (inputEmail.length > 50 || !emailRegex.test(inputEmail)) {
        alert("Email inválido! Verifique o formato e o tamanho máximo (50 caracteres).");
        return;
    }

    // Valida a senha (mínimo 6)
    if (inputPassword.length < 6) {
        alert("A senha deve ter no mínimo 6 caracteres.");
        return;
    }

    const user = {
        name: inputName,
        email: inputEmail,
        password: inputPassword
    };

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
    .then(() => {
        alert("Usuário cadastrado com sucesso!");
        window.location.href = "login.html";
    })
    .catch(error => {
        console.error("Erro ao cadastrar:", error);
        alert("Erro ao cadastrar usuário. Tente novamente.");
    });
}


function sanitize(input) {
    const div = document.createElement("div");
    div.textContent = input;
    return div.innerHTML;
}

