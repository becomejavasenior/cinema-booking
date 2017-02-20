(function () {

    let locationPathName = window.location.pathname;
    let n = locationPathName.lastIndexOf('/');
    let filmId = locationPathName.substring(n + 1);

    getSession(filmId);

    function getSession(filmId) {
        let session = {};

        $.ajax({
            url: '/getSeans/' + filmId,
            method: 'GET',
            success: function (response) {
                // console.log(response);

                $(".page-header").text(response[0].movie.name);

                for (var i=0; i<response.length; i++) {

                    let hrefToSeats = "/seats.html?seansId="+response[i].id;
                    $(".list-seans").append(
                        "<div class=\"seans\">" +
                        "<p>" + response[i].start.hour + ":" +response[i].start.minute + "</p>" +
                         "<a class=\"btn btn-primary\" href="+hrefToSeats+">Выбрать место<span class=\"glyphicon glyphicon-chevron-right\"></span></a>" +
                        "</div>"
                    )
                }
            }
        });
    }
})();