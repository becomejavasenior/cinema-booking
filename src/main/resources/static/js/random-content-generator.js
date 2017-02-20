(function (window) {

    let drawRandom = {
        draw(){
            let $images = $(document).find('.portfolio-item img');

            $images.each(function () {
                let $image = $(this);

                $.ajax({
                    url: 'https://random-movie.herokuapp.com/random',
                    dataType: "jsonp",
                    success: function (response) {
                        drawImg($image, response.Poster)
                    }
                });
            });
        }
    };


    function drawImg($image, src) {
        $image.attr('src', src)
    }

    window.drawRandom = drawRandom;

})(window);
