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
        fetch(url, settings)
            .then(response => response.text()) // Cambiar a .text() en lugar de .json()
            .then(data => {
                console.log("Turno actualizado:", data);
                // Aquí puedes agregar lógica adicional para manejar la respuesta
                alert(data); // Muestra una alerta con el mensaje de respuesta
            })
            .catch(error => {
                console.error("Error actualizando el turno:", error);
            });
    });
});

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

            // Llenar select de Odontólogos si están definidos
            if (turno.odontologos && turno.odontologos.length > 0) {
                let odontologoSelect = document.querySelector('#odontologo');
                odontologoSelect.innerHTML = '';
                turno.odontologos.forEach(odontologo => {
                    let option = document.createElement('option');
                    option.value = odontologo.id;
                    option.textContent = `${odontologo.nombre} ${odontologo.apellido} (Matrícula: ${odontologo.matricula})`;
                    odontologoSelect.appendChild(option);
                });
            }

            // Llenar select de Pacientes si están definidos
            if (turno.pacientes && turno.pacientes.length > 0) {
                let pacienteSelect = document.querySelector('#paciente');
                pacienteSelect.innerHTML = '';
                turno.pacientes.forEach(paciente => {
                    let option = document.createElement('option');
                    option.value = paciente.id;
                    option.textContent = `${paciente.nombre} ${paciente.apellido} (Cédula: ${paciente.cedula})`;
                    pacienteSelect.appendChild(option);
                });
            }

            // Establecer la fecha del turno
            document.querySelector('#fecha').value = turno.fecha;

            // Mostrar el formulario de actualización
            document.querySelector('#div_paciente_updating').style.display = "block";
        })
        .catch(error => {
            console.error("Error encontrando el turno:", error);
        });

    // Obtener lista de todos los odontólogos
    const urlOdontologos = '/odontologos';

    fetch(urlOdontologos)
        .then(response => response.json())
        .then(odontologos => {
            let odontologoSelect = document.querySelector('#odontologo');
            odontologoSelect.innerHTML = '';
            odontologos.forEach(odontologo => {
                let option = document.createElement('option');
                option.value = odontologo.id;
                option.textContent = `${odontologo.nombre} ${odontologo.apellido} (Matrícula: ${odontologo.matricula})`;
                odontologoSelect.appendChild(option);
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
            pacientes.forEach(paciente => {
                let option = document.createElement('option');
                option.value = paciente.id;
                option.textContent = `${paciente.nombre} ${paciente.apellido} (Cédula: ${paciente.cedula})`;
                pacienteSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error("Error obteniendo lista de pacientes:", error);
        });
}