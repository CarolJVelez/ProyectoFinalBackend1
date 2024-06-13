window.addEventListener('load', function () {
    (function(){
        const url = '/turnos';
        const settings = {
            method: 'GET'
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                var tableBody = document.getElementById("TurnoTableBody");
                tableBody.innerHTML = ''; // Limpiar la tabla antes de agregar filas

                // Recorrer la colección de turnos del JSON
                data.forEach(turno => {
                    let turnoRow = document.createElement('tr');
                    turnoRow.id = 'tr_' + turno.id;

                    let deleteButton = '<button' +
                        ' id=' + '\"' + 'btn_delete_' + turno.id + '\"' +
                        ' type="button" onclick="deleteBy(' + turno.id + ')" class="btn btn-danger btn_delete">' +
                        '&times' +
                        '</button>';

                    let updateButton = '<button' +
                        ' id=' + '\"' + 'btn_id_' + turno.id + '\"' +
                        ' type="button" onclick="findBy(' + turno.id + ')" class="btn btn-info btn_id">' +
                        turno.id +
                        '</button>';

                    let odontologo = turno.odontologo.nombre + ' ' + turno.odontologo.apellido + ' (Matrícula: ' + turno.odontologo.matricula + ')';
                    let paciente = turno.paciente.nombre + ' ' + turno.paciente.apellido + ' (Cédula: ' + turno.paciente.cedula + ')';
                    let domicilio = turno.paciente.domicilio.calle + ' ' + turno.paciente.domicilio.numero + ', ' +
                        turno.paciente.domicilio.localidad + ', ' + turno.paciente.domicilio.provincia;

                    let fecha = new Date(turno.fecha).toLocaleDateString('es-ES', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit'
                    });


                    turnoRow.innerHTML = '<td>' + updateButton + '</td>' +
                        '<td>' + odontologo + '</td>' +
                        '<td>' + paciente + '<br>' +
                        'Fecha de Ingreso: ' + turno.paciente.fechaIngreso + '<br>' +
                        'Domicilio: ' + domicilio + '<br>' +
                        'Email: ' + turno.paciente.email + '</td>' +
                        '<td>' + turno.fecha+ '</td>' +
                        '<td>' + deleteButton + '</td>';

                    tableBody.appendChild(turnoRow);
                });
            })
            .catch(error => {
                console.error('Error fetching turnos:', error);
                alert('Error fetching turnos');
            });
    })();

    (function(){
        let pathname = window.location.pathname;
        if (pathname == "/get_turnos.html") {
            let navLink = document.querySelector(".nav .nav-item a:last-child");
            if (navLink) {
                navLink.classList.add("active");
            }
        }
    })();
});
