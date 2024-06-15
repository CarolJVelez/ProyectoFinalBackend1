window.addEventListener('load', function () {

    // Obtener el formulario de actualización de turnos
    const formulario = document.querySelector('#update_turnos_form');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevenir el envío del formulario

        // Obtener el ID del turno
        let turnoId = document.querySelector('#turno_id').value;

        // Crear un objeto con los datos del turno
        const formData = {
            id: turnoId,
            odontologo: {
                id: document.querySelector('#odontologo').value
            },
            paciente: {
                id: document.querySelector('#paciente').value
            },
            fecha: document.querySelector('#fecha').value
        };

        // Configuración para la petición PUT
        const url = '/turnos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        };

        // Realizar la petición PUT para actualizar el turno
        if (confirm("¿Estás seguro de que deseas actualizar este turno?")) {
        fetch(url, settings)
            .then(response => response.text()) // Cambiar a .text() en lugar de .json()
            .then(data => {
                console.log("Turno actualizado:", data);
                mostrarMensaje(data, 'success');
                // Recarga la página después de un breve retraso
                setTimeout(() => {
                    window.location.reload();
                }, 2000);
                })
            .catch(error => {
                console.error("Error actualizando el turno:", error);
                mostrarMensaje('Error actualizando el turno: ' + error.message, 'danger');
            });
        }
    });
});

function mostrarMensaje(mensaje, tipo) {
    const responseDiv = document.querySelector('#response');
    responseDiv.innerHTML = `
        <div class="alert alert-${tipo} alert-dismissible fade show" role="alert">
            ${mensaje}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>`;
    responseDiv.style.display = 'block';
    setTimeout(() => {
        responseDiv.style.display = 'none';
    }, 5000);
}
// Función para encontrar un turno por ID y llenar el formulario
function findBy(id) {
    const urlTurno = '/turnos/buscar/' + id;
    const settingsTurno = {
        method: 'GET'
    };

    // Obtener el turno por ID
    fetch(urlTurno, settingsTurno)
        .then(response => response.json())
        .then(turno => {
            document.querySelector('#turno_id').value = turno.id || '';

            // Obtener lista de todos los odontólogos
            const urlOdontologos = '/odontologos';
            fetch(urlOdontologos)
                .then(response => response.json())
                .then(odontologos => {
                    let odontologoSelect = document.querySelector('#odontologo');
                    odontologoSelect.innerHTML = '';

                    // Primero, agregar el odontólogo actual del turno
                    let currentOdontologo = turno.odontologo;
                    let option = document.createElement('option');
                    option.value = currentOdontologo.id;
                    option.textContent = `${currentOdontologo.nombre} ${currentOdontologo.apellido} (Matrícula: ${currentOdontologo.matricula})`;
                    odontologoSelect.appendChild(option);

                    // Luego, agregar el resto de los odontólogos
                    odontologos.forEach(odontologo => {
                        if (odontologo.id !== currentOdontologo.id) {
                            let option = document.createElement('option');
                            option.value = odontologo.id;
                            option.textContent = `${odontologo.nombre} ${odontologo.apellido} (Matrícula: ${odontologo.matricula})`;
                            odontologoSelect.appendChild(option);
                        }
                    });
                })
                .catch(error => {
                    console.error("Error obteniendo lista de odontólogos:", error);
                });

            // Obtener lista de todos los pacientes
            const urlPacientes = '/paciente';
            fetch(urlPacientes)
                .then(response => response.json())
                .then(pacientes => {
                    let pacienteSelect = document.querySelector('#paciente');
                    pacienteSelect.innerHTML = '';

                    // Primero, agregar el paciente actual del turno
                    let currentPaciente = turno.paciente;
                    let option = document.createElement('option');
                    option.value = currentPaciente.id;
                    option.textContent = `${currentPaciente.nombre} ${currentPaciente.apellido} (Cédula: ${currentPaciente.cedula})`;
                    pacienteSelect.appendChild(option);

                    // Luego, agregar el resto de los pacientes
                    pacientes.forEach(paciente => {
                        if (paciente.id !== currentPaciente.id) {
                            let option = document.createElement('option');
                            option.value = paciente.id;
                            option.textContent = `${paciente.nombre} ${paciente.apellido} (Cédula: ${paciente.cedula})`;
                            pacienteSelect.appendChild(option);
                        }
                    });
                })
                .catch(error => {
                    console.error("Error obteniendo lista de pacientes:", error);
                });

            // Establecer la fecha del turno
            document.querySelector('#fecha').value = turno.fecha;

            // Mostrar el formulario de actualización
            document.querySelector('#div_paciente_updating').style.display = "block";
        })
        .catch(error => {
            console.error("Error encontrando el turno:", error);
        });
}
