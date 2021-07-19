$(document).ready(function () {
    $('body').on('click', '.delete-post', function () {
        let id = $(this).attr("value");
        let result = confirm("Are you sure?");
        if (result) {
            $(this).closest(".card-box").remove();
            $.ajax({
                type: "post",
                url: "controller",
                data: {
                    "command": "delete_post",
                    "post_id": id
                },
                success: function (data) {
                }
            });
        }
    });

    $('.like-check').on('change',  function () {
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

//
//
//     $('#filter_btn').on('click', function () {
//         $.ajax({
//             type: "post",
//             url: "filter",
//             data: {
//                 filter_author_text: $('#filter_author').val(),
//                 filter_date_text: $('#filter_date').val(),
//                 filter_hashtags_text: $('#filter_hashtag').val(),
//             },
//             success: function (response) {
//                 $('#post_container').empty();
//                 $('#post_container').append(response);
//                 $('#more_btn').remove();
//             }
//         });
//     });
//
});