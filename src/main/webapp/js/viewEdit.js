function pasteValuesToForm(form,post){
    form.model.value = post.model;
    form.height.value = post.height === " - " ? "" : post.height;
    form.lengthInput.value = post.length === " - " ? "" : post.length;
    form.wingspan.value = post.wingspan === " - " ? "" : post.wingspan;
    form.crew.value = post.crew === " - " ? "" : post.crew;
    form.price.value = post.price;
    form.origin.value = post.origin === " - " ? "" : post.origin;
    form.speed.value = post.speed === " - " ? "" : post.speed;
    form.dist.value = post.dist === " - " ? "" : post.dist;
    form.hashtags.value = post.hashtags === "" ? [] : post.hashtags.join(' ');
}