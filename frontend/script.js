const API_URL = "http://localhost:8080/expenses"
let expenseIdToUpdate = null;

document.addEventListener("DOMContentLoaded", loadExpenses);
document.addEventListener("DOMContentLoaded", () => {
    const  params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    if(id){
        loadExpenseById(id);
    }

});

function loadExpenses(){
   fetch(API_URL)
   .then(response => response.json())
   .then(data => renderExpense(data))
   .catch(error => console.error("Error fetching expenses:", error));
}

function renderExpense(expenses){
    const tableBody = document.getElementById("expense-list")
    const totalSpan = document.getElementById("total");

    tableBody.innerHTML = "";
    let total = 0;

    expenses.forEach(e => {
         
        const date = e.moment.split("T")[0];
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${e.description}</td>
            <td>${e.amount.toFixed(2)}</td>
            <td>${e.category.name}</td>
            <td>${date}</td>
            <td>
                <button onclick="Update(${e.id})">Atualizar</button>
                <button onclick="Delete(${e.id})">Deletar</button>
            </td>
        `;
        tableBody.appendChild(tr);
        total += e.amount;
        
    });

    totalSpan.textContent = total.toFixed(2);
}

function loadExpenseById(id){

    fetch(`${API_URL}/${id}`)
    .then(response => response.json())
    then(data =>{
         document.getElementById("description").value = data.description;
         document.getElementById("amount").value = data.amount;
         document.getElementById("category").value = data.category.name;
         expenseIdToUpdate = data.id;
    })
}

function Update(id){
    window.location.href = `insert.html?id=${id}`;
}

function addExpense(){
   const description = document.getElementById("description").value.trim();
   const amount = document.getElementById("amount").value;
   const category = document.getElementById("category").value.trim();

   if(!description || !(amount) || !category){
         alert("Por favor, preencha todos os campos.");
         return;
   }

   const method = expenseIdToUpdate ? "PUT" : "POST";
   const url = expenseIdToUpdate ? `${API_URL}/${expenseIdToUpdate}` : API_URL;

   const expense ={
        description: description,
        amount: Number(amount),
        moment: new Date().toISOString(),
        category: {
            name: category
        }
   }

   fetch(url,{
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
   body : JSON.stringify(expense)
   })
   .then(response => {
        if(!response.ok){
            throw new Error("Erro ao salvar despesa.");
        }
        return response.json();
    })
    .then(() => {
        loadExpenses();
        clearForm();
        window.location.href = "index.html";
        expenseIdToUpdate = null;
   })
   .catch(error => {
        alert("Erro ao salvar despesa: " + error.message);
   });
}

function Delete(id){
    fetch(`${API_URL}/${id}`,{
        method: "DELETE"
    })
    .then(response =>{
        if(!response.ok){
            throw new Error("Erro ao deletar despesa.");
        }
    })
    .then(() => {
        loadExpenses();
   })
   .catch(error => {
        alert("Erro ao deletar despesa: " + error.message);
   });
}