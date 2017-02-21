(function (window) {
    console.log("random-contend-generator");

    let drawRandom = {
        draw(){
            let $images = $(document).find('.portfolio-item');

            $images.each(function () {
                let $portfolioItem = $(this);

                $.ajax({
                    url: 'https://random-movie.herokuapp.com/random',
                    dataType: "jsonp",
                    success: function (response) {
                        drawInfo($portfolioItem, response)
                    }
                });
            });
        }
    };

    function drawInfo($portfolioItem, film) {
        $portfolioItem.find('img').attr('src', film.Poster);
        $portfolioItem.find('h3 a').text(film.Title);
        $portfolioItem.find('p').text(film.Plot);
        $portfolioItem.find('small').text(film.Genre);
    }

    window.drawRandom = drawRandom;

})(window);
