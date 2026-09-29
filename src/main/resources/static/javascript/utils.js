//toggler button
document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll('.switch-container').forEach(container => {
        const checkbox = container.querySelector('input[type="checkbox"]');
        const texto = container.querySelector('.estado-label');

        function actualizarEstado() {
            if (checkbox.checked) {
                texto.textContent = "Desactivar";


            } else {
                texto.textContent = "Activar";
            }
        }

        actualizarEstado();
        checkbox.addEventListener('change', actualizarEstado);
    });
});

//modales
function cerrarModal(id) {
    document.getElementById(id).style.display = 'none';
}

// Mostrar automáticamente el modal si existe
window.onload = function () {
    if (document.getElementById("notificacionModal")) {
        document.getElementById("notificacionModal").style.display = "block";
    }
    if (document.getElementById("confirmacionModal")) {
        document.getElementById("confirmacionModal").style.display = "block";
    }
}