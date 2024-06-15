window.addEventListener('load', function () {
    (function(){
        const url = '/odontologos';
        const settings = {
            method: 'GET'
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                var tableBody = document.getElementById("OdontologoTableBody");
                tableBody.innerHTML = ''; // Limpiar la tabla antes de agregar filas

                // Recorrer la colección de odontólogos del JSON
                data.forEach(odontologo => {
                    let odontologoRow = document.createElement('tr');
                    odontologoRow.id = 'tr_' + odontologo.id;

                    let deleteButton = '<button' +
                        ' id=' + '\"' + 'btn_delete_' + odontologo.id + '\"' +
                        ' type="button" onclick="deleteBy(' + odontologo.id + ')" class="btn btn-danger btn_delete">' +
                        '&times' +
                        '</button>';

                    let updateButton = '<button' +
                        ' id=' + '\"' + 'btn_id_' + odontologo.id + '\"' +
                        ' type="button" onclick="findBy(' + odontologo.id + ')" class="btn btn-info btn_id">' +
                        odontologo.id +
                        '</button>';

                    odontologoRow.innerHTML = '<td>' + updateButton + '</td>' +
                        '<td class=\"td_matricula\">' + odontologo.matricula.toUpperCase() + '</td>' +
                        '<td class=\"td_nombre\">' + odontologo.nombre.toUpperCase() + '</td>' +
                        '<td class=\"td_apellido\">' + odontologo.apellido.toUpperCase() + '</td>' +
                        '<td>' + deleteButton + '</td>';

                    tableBody.appendChild(odontologoRow);
                });
            });
    })();

    (function(){
        let pathname = window.location.pathname;
        if (pathname == "/get_odontologos.html") {
            let navLink = document.querySelector(".nav .nav-item a:last-child");
            if (navLink) {
                navLink.classList.add("active");
            }
        }
    })();
});



