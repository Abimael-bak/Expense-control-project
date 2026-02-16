
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


