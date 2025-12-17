document.addEventListener("DOMContentLoaded", () => {
    const nameInput = document.getElementById("name");
    const priceInput = document.getElementById("price");
    const frequencyInput = document.getElementById("frequency");
    const startDateInput = document.getElementById("startDate");
    const form = document.querySelector("form");

    form.addEventListener("submit", (event) => {
        const name = nameInput.value.trim();
        const price = parseFloat(priceInput.value);
        const frequency = frequencyInput.value;
        const startDate = startDateInput.value ? new Date(startDateInput.value) : null;

        const today = new Date();
        today.setHours(0, 0, 0, 0);

        if (name.length === 0) {
            alert("Il nome non può essere vuoto.");
            event.preventDefault();
            return;
        }

        if (name.length < 2) {
            alert("Il nome deve contenere almeno 2 caratteri.");
            event.preventDefault();
            return;
        }

        if (isNaN(price) || price <= 0) {
            alert("Il prezzo deve essere un numero maggiore di zero.");
            event.preventDefault();
            return;
        }

        if (!frequency) {
            alert("Seleziona una frequenza.");
            event.preventDefault();
            return;
        }

        if (!startDate) {
            alert("La data di inizio è obbligatoria.");
            event.preventDefault();
            return;
        }

        const minDate = new Date(today);

        switch (frequency) {
            case "WEEKLY":
                minDate.setDate(today.getDate() - 7);
                break;
            case "MONTHLY":
                minDate.setMonth(today.getMonth() - 1);
                break;
            case "YEARLY":
                minDate.setFullYear(today.getFullYear() - 1);
                break;
        }

        if (startDate > today) {
            alert("La data di inizio non può essere futura.");
            event.preventDefault();
            return;
        }

        if (startDate < minDate) {
            alert("La data di inizio non è valida per la frequenza selezionata.");
            event.preventDefault();
            return;
        }
    });
});
