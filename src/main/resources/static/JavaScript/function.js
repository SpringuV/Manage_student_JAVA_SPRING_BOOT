// set timeout welcome
document.addEventListener("DOMContentLoaded", function() {
    var element = document.getElementById("autoHide");
        setTimeout(function(){
            element.classList.add("hidden");
        }, 3000); // 3s
    }
);
