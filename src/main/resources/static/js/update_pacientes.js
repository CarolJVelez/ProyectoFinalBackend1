window.addEventListener('load', function () {

    // Obtener el formulario de actualización del paciente
    const formulario = document.querySelector('#update_paciente_form');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevenir el envío del formulario

        // Obtener el ID del paciente
        let pacienteId = document.querySelector('#paciente_id').value;

        // Crear un objeto con los datos del paciente
        const formData = {
            id: pacienteId,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            cedula: document.querySelector('#cedula').value,
            fechaIngreso: document.querySelector('#fechaIngreso').value,
            domicilio: {
                calle: document.querySelector('#calle').value,
                numero: document.querySelector('#numero').value,
                localidad: document.querySelector('#localidad').value,
                provincia: document.querySelector('#provincia').value
            },
            email: document.querySelector('#email').value
        };

        // Configuración para la petición PUT
        const url = '/paciente';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        // Realizar la petición PUT para actualizar el paciente
        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                console.log("Paciente actualizado:", data);
                // Aquí puedes agregar lógica adicional para manejar la respuesta
            })
            .catch(error => {
                console.error("Error actualizando el paciente:", error);
            });
    });
});

// Función para encontrar un paciente por ID y llenar el formulario
function findBy(id) {
    const url = '/paciente/' + id;
    const settings = {
        method: 'GET'
    }

    fetch(url, settings)
        .then(response => response.json())
        .then(data => {
            let paciente = data;
            document.querySelector('#paciente_id').value = paciente.id || '';
            document.querySelector('#nombre').value = paciente.nombre || '';
            document.querySelector('#apellido').value = paciente.apellido || '';
            document.querySelector('#cedula').value = paciente.cedula || '';
            document.querySelector('#fechaIngreso').value = paciente.fechaIngreso || '';
            document.querySelector('#calle').value = paciente.domicilio.calle || '';
            document.querySelector('#numero').value = paciente.domicilio.numero || '';
            document.querySelector('#localidad').value = paciente.domicilio.localidad || '';
            document.querySelector('#provincia').value = paciente.domicilio.provincia || '';
            document.querySelector('#email').value = paciente.email || '';
            // Mostrar el formulario de actualización
            document.querySelector('#div_paciente_updating').style.display = "block";
        })
        .catch(error => {
            console.error("Error encontrando el paciente:", error);
        });
}
