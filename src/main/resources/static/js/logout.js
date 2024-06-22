function logout(){
    let cerrarSesion = confirm("¿Desea cerrar sesión?")
    if(cerrarSesion){
    window.location.href = "/logout"
    }
}