window.addEventListener('load', function () {

    //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    //los datos que el usuario cargará de la nueva pelicula
    const formulario = document.querySelector('#add_new_odontologo');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

       //creamos un JSON que tendrá los datos de la nueva película
        const formData = {
            matricula: document.querySelector('#matricula').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,

        };
        //invocamos utilizando la función fetch la API peliculas con el método POST que guardará
        //la película que enviaremos en formato JSON
        const url = '/odontologos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text);
                    });
                }
                return response.json();
            })
            .then(data => {

                const responseDiv = document.querySelector('#response');
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Odontologo agregado correctamente</strong></div>';

                responseDiv.innerHTML = successAlert;
                responseDiv.style.display = "block";
                resetUploadForm();
                //Para que se cierre en 3seg
                setTimeout(function() {
                    responseDiv.style.display = "none";
                }, 3000);
            })
            .catch(error => {

                console.error('Error en la solicitud:', error);

                const responseDiv = document.querySelector('#response');
                let errorMessage = error.message;

                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    `<strong>${errorMessage}</strong></div>`;

                responseDiv.innerHTML = errorAlert;
                responseDiv.style.display = "block";

                //Para que se cierre en 3seg
                setTimeout(function() {
                    responseDiv.style.display = "none";
                }, 3000);
            });
    });


    function resetUploadForm(){
        document.querySelector('#matricula').value = "";
        document.querySelector('#nombre').value = "";
         document.querySelector('#apellido').value = "";

    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/get_odontologos.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});