$(document).ready(function() {
    $("#connecter").click(function(e) {
        e.preventDefault();
        var verif = true;
        var email = $("#email").val();
        var password = $("#password").val();

        if (email == "") {
            $("#email").css("border", "1px solid red");
            verif = false;
        } else {
            $("#email").css("border", "1px solid #d4d4d4");
        }

        if (password == "") {
            $("#password").css("border", "1px solid red");
            verif = false;
        } else {
            $("#password").css("border", "1px solid #d4d4d4");
        }

        if (verif) {
        }
    });
});

