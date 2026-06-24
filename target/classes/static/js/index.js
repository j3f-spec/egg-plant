let dropdownBtn = document.querySelector(".navbar-toggler");

dropdownBtn.addEventListener("click", (e)=>{
    const dropdownMenu = document.getElementById("navbarNav");
        if(dropdownMenu.style.display === "block"){
            dropdownMenu.style.display = "none";
        }else{
            dropdownMenu.style.display = "block";
        }    
});
                