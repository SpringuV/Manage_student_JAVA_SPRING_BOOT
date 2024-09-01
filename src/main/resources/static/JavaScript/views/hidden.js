document.addEventListener('DOMContentLoaded', function () {
// set timeout welcome
    const element = document.getElementById("autoHide");
    setTimeout(function () {
        element.classList.add("hidden");
    }, 3000); // 3s
});
