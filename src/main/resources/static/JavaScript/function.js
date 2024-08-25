// set timeout welcome
document.addEventListener("DOMContentLoaded", function() {
    const element = document.getElementById("autoHide");
        setTimeout(function(){
            element.classList.add("hidden");
        }, 3000); // 3s
    }
);
