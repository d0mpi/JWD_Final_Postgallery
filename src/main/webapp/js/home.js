$(document).ready(function () {
    $('body').on('click', '.card-delete-button', function () {
        let id = $(this).attr("value");
        let result = confirm("Are you sure?");
        if (result) {
            $(this).closest(".card-box").remove();
            $.ajax({
                type: "post",
                url: "delete",
                data: "id=" + id,
                success: function (data) {
                }
            });
        }
    });

    $('#more_btn').on('click', function () {
        $.ajax({
            type: "post",
            url: "more",
            success: function (response) {
                $('#post_container').append(response);
                $.ajax({
                    type: "get",
                    url: "more",
                    success: function (response) {
                        if (response === "true") {
                            $('#more_btn').remove();
                        }
                    }
                });
            }
        });
    });


    $('#filter_btn').on('click', function () {
        $.ajax({
            type: "post",
            url: "filter",
            data: {
                filter_author_text: $('#filter_author').val(),
                filter_date_text: $('#filter_date').val(),
                filter_hashtags_text: $('#filter_hashtag').val(),
                filter_author_checkbox: $('#filter_author_check').is(":checked"),
                filter_date_checkbox: $('#filter_date_check').is(":checked"),
                filter_hashtags_checkbox: $('#filter_hashtags_check').is(":checked")
            },
            success: function (response) {
                $('#post_container').empty();
                $('#post_container').append(response);
                $('#more_btn').remove();
            }
        });
    });

});