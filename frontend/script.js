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

