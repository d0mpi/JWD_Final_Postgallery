$(document).ready(function () {
    $('#register_btn').on('click', function () {
        $.ajax({
            type: "post",
            url: "registration",
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