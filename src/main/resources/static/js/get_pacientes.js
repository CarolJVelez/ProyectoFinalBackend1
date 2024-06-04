window.addEventListener('load', function () {
    (function(){
        const url = 'http://localhost:8080/paciente';
        const settings = {
            method: 'GET'
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                var tableBody = document.getElementById("PacienteTableBody");
                tableBody.innerHTML = ''; // Limpiar la tabla antes de agregar filas

                // Recorrer la colección de pacientes del JSON
                data.forEach(paciente => {
                    let pacienteRow = document.createElement('tr');
                    pacienteRow.id = 'tr_' + paciente.id;

                    let deleteButton = '<button' +
                        ' id=' + '\"' + 'btn_delete_' + paciente.id + '\"' +
                        ' type="button" onclick="deleteBy(' + paciente.id + ')" class="btn btn-danger btn_delete">' +
                        '&times' +
                        '</button>';

                    let updateButton = '<button' +
                        ' id=' + '\"' + 'btn_id_' + paciente.id + '\"' +
                        ' type="button" onclick="findBy(' + paciente.id + ')" class="btn btn-info btn_id">' +
                        paciente.id +
                        '</button>';

                    let domicilio = paciente.domicilio.calle + ' ' + paciente.domicilio.numero + ', ' +
                        paciente.domicilio.localidad + ', ' + paciente.domicilio.provincia;

                    pacienteRow.innerHTML = '<td>' + updateButton + '</td>' +
                        '<td class=\"td_nombre\">' + paciente.nombre.toUpperCase() + '</td>' +
                        '<td class=\"td_apellido\">' + paciente.apellido.toUpperCase() + '</td>' +
                        '<td class=\"td_cedula\">' + paciente.cedula.toUpperCase() + '</td>' +
                        '<td class=\"td_fecha_ingreso\">' + paciente.fechaIngreso.toUpperCase() + '</td>' +
                        '<td class=\"td_domicilio\">' + domicilio.toUpperCase() + '</td>' +
                        '<td class=\"td_email\">' + paciente.email.toUpperCase() + '</td>' +
                        '<td>' + deleteButton + '</td>';

                    tableBody.appendChild(pacienteRow);
                });
            });
    })();

    (function(){
        let pathname = window.location.pathname;
        if (pathname == "/get_pacientes.html") {
            let navLink = document.querySelector(".nav .nav-item a:last-child");
            if (navLink) {
                navLink.classList.add("active");
            }
        }
    })();
});