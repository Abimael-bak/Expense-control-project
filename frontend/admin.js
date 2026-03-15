const API_URL = "http://localhost:8080";

// ----------------------
// TOKEN
// ----------------------

function getToken(){
    return localStorage.getItem("token");
}


function getUserId() {

    const token = getToken();

    if (!token) return null;

    if (typeof jwt_decode === "undefined") return null;

    const decoded = jwt_decode(token);

    return decoded.sub;
}


// ----------------------
// CARREGAMENTO INICIAL
// ----------------------

document.addEventListener("DOMContentLoaded", () => {
    loadUsers();
});

// ----------------------
// LISTAR USUÁRIOS
// ----------------------

function loadUsers(){

    const token = getToken();

    fetch(`${API_URL}/users`,{
        headers:{
            Authorization:`Bearer ${token}`
        }
    })
    .then(res => res.json())
    .then(renderUsers)
    .catch(err => console.error("Erro ao carregar usuários:",err));
}

// ----------------------
// RENDER USERS
// ----------------------

function renderUsers(users){

    const table = document.getElementById("user-list");

    table.innerHTML = "";

    users.forEach(user =>{

        const tr = document.createElement("tr");

        tr.innerHTML = `
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>
            <button onclick="deleteUser(${user.id})">
                Deletar
            </button>
        </td>
        `;

        table.appendChild(tr);

    });

}

// ----------------------
// DELETAR USUÁRIO
// ----------------------

function deleteUser(id){

    const token = getToken();
    const userId = id;
    if(!confirm("Deseja realmente deletar esse usuário?")){
        return;
    }

    fetch(`${API_URL}/users/${userId}`,{
        method:"DELETE",
        headers:{
            Authorization:`Bearer ${token}`
        }
    })
    .then(res =>{

        if(!res.ok){
            throw new Error("Erro ao deletar usuário");
        }

        loadUsers();

    })
    .catch(err => alert("Erro ao deletar usuário"));

}

// ----------------------
// BUSCAR USUÁRIO
// ----------------------

function searchUsers(){

    const value = document
    .getElementById("search-user")
    .value
    .toLowerCase()
    .trim();

    if(!value){
        loadUsers();
        return;
    }

    const token = getToken();

    fetch(`${API_URL}/users`,{
        headers:{
            Authorization:`Bearer ${token}`
        }
    })
    .then(res => res.json())
    .then(data =>{

        const filtered = data.filter(u =>
            u.name.toLowerCase().includes(value) ||
            u.email.toLowerCase().includes(value)
        );

        renderUsers(filtered);

    });

}