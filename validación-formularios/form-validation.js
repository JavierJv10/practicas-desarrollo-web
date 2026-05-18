document.getElementById("registrationForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevenir el envío del formulario

    // Limpiar mensajes de error previos
    clearErrors();

    // Obtener los valores de los campos
    const fullName = document.getElementById("fullName").value.trim();
    const email = document.getElementById("email").value.trim();
    const age = document.getElementById("age").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const gender = document.getElementById("gender").value;
    const birthdate = document.getElementById("birthdate").value;
    const comments = document.getElementById("comments").value.trim();

    let isValid = true;

    // Validación de Nombre Completo
    if (!fullName || !/^[A-Z][a-z]+(\s[A-Z][a-z]+){2,}$/.test(fullName)) {
        document.getElementById("errorFullName").textContent = "El nombre debe tener al menos 3 palabras con mayúsculas al inicio.";
        isValid = false;
    }

    // Validación de Correo Electrónico
    if (!email || !/\S+@\S+\.\S+/.test(email)) {
        document.getElementById("errorEmail").textContent = "Debe ingresar un correo electrónico válido.";
        isValid = false;
    }

    // Validación de Edad
    if (!age || age < 18 || age > 100 || !Number.isInteger(+age)) {
        document.getElementById("errorAge").textContent = "La edad debe ser un número entre 18 y 100.";
        isValid = false;
    }

    // Validación de Número de Teléfono
    if (!phone || !/^\d{10}$/.test(phone)) {
        document.getElementById("errorPhone").textContent = "El número de teléfono debe tener 10 dígitos numéricos.";
        isValid = false;
    }

    // Validación de Género
    if (!gender) {
        document.getElementById("errorGender").textContent = "Debe seleccionar un género.";
        isValid = false;
    }

    // Validación de Fecha de Nacimiento (Debe ser mayor de 18 años)
    if (!birthdate || !isOlderThan18(birthdate)) {
        document.getElementById("errorBirthdate").textContent = "Debe ser mayor de 18 años.";
        isValid = false;
    }

    // Validación de Comentarios
    if (comments.length > 300) {
        document.getElementById("errorComments").textContent = "Los comentarios no pueden exceder los 300 caracteres.";
        isValid = false;
    }

    // Si todo es válido, mostrar mensaje de éxito
    if (isValid) {
        document.getElementById("formMessage").textContent = "Formulario enviado con éxito.";
    }
});

// Función para verificar si la persona tiene más de 18 años
function isOlderThan18(birthdate) {
    const today = new Date();
    const birthDate = new Date(birthdate);
    const age = today.getFullYear() - birthDate.getFullYear();
    const month = today.getMonth() - birthDate.getMonth();
    return (age > 18 || (age === 18 && month >= 0));
}

// Limpiar los mensajes de error
function clearErrors() {
    document.getElementById("errorFullName").textContent = "";
    document.getElementById("errorEmail").textContent = "";
    document.getElementById("errorAge").textContent = "";
    document.getElementById("errorPhone").textContent = "";
    document.getElementById("errorGender").textContent = "";
    document.getElementById("errorBirthdate").textContent = "";
    document.getElementById("errorComments").textContent = "";
    document.getElementById("formMessage").textContent = "";
}