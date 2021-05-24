$(document).ready(function () {
    $('#sign_btn').on('click', function () {
        $.ajax({
            type: "post",
            url: "sign",
            data: {
                username: $('#username').val(),
                password: $('#password').val()
            },
            success: function (response) {
                if (response !== 'success') {
                    alert(response);
                } else {
                    window.location.replace("http://localhost:8080/home");
                }
            }
        });
    });
});