$(document).ready(function () {
    $('body').on('click', '.delete-post', function () {
        let id = $(this).attr("value");
        let result = confirm("Are you sure?");
        if (result) {
            $.ajax({
                type: "post",
                url: "controller",
                data: {
                    "command": "delete_post",
                    "post_id": id
                },
                success: function () {
                    $(this).closest(".card-box").remove();
                }
            });
        }
    });

    $('.like-check').on('change', function () {
        let isLike = $(this).is(':checked');
        let id = $(this).attr("value");
        $.ajax({
            type: "post",
            url: "controller",
            data: {
                "command": "like",
                "post_id": id,
                "isLike": isLike
            },
            success: function (data) {
            }
        });
    });

    let is_big = 0;
    $('.overlay').on('click', function () {
        $('.card-img').removeClass("card-img-clicked").addClass('resize');
        $('.overlay').hide();
        is_big = 0;
    });

    $('.card-img').on('click', function () {
        if (is_big === 0) {
            $(this).addClass("card-img-clicked").removeClass('resize');
            $('.overlay').show();
            is_big = 1;
        }
    });


});
