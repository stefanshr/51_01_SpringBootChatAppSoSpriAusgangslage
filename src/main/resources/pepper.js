console.log("hi")
window.onload = function () {
    document.getElementById("loginForm").onsubmit = function () {
        var pswElement = document.getElementById("password");
        pswElement.value = pswElement.value + 'pepper';
        return true;
    };
};