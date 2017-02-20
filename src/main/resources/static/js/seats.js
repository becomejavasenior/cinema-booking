(function () {
    let urlParams = new URLSearchParams(window.location.search);
    let seansId = urlParams.get('seansId');

    let placeNumber = 0;
    let s1 = {};

    getSession(seansId);
    setHref(seansId);

    function getSession(seansId) {
        let session = {};
        $.ajax({
            url: '/getSeats/' + seansId,
            method: 'GET',
            success: function (response) {
                session.price = response.price;
                session.seansId = seansId;
                session.filmName = response.filmName;
                session.filmDate = response.filmDate;
                session.map = response.map;

                session.arrMap = [];
                Object.values(session.map).forEach(value => session.arrMap.push(value));

                console.log('Результат запроса');
                console.log(session);

                drawInfo(session);
                drawSeats(session);
                s1 = session;
            }
        });
    }

    function setHref(seansId) {

        let myHref = $('#hrefToSchedule');
        $.ajax({

            url: '/getFilmIdBySeansId/' + seansId,
            method: 'GET',
            success: function (response) {
                myHref.attr('href', "/seans/" + response.id);
                myHref.text("Сеансы " + response.name);
                $('#hrefToSchedule').val(myHref);
                return response;
            }
        });
    }


    function getUnavailable(map) {
        let unavailable = [];

        for (let i = 0, row = 0, seat = 1; i < map.length; i++) {

            if (i % 10 == 0) {
                row++;
                seat = 1;
            }

            if (map[i] === false) {
                unavailable.push(row + '_' + seat);
            }
            seat++;
        }

        return unavailable;
    }


    function drawSeats(session) {
        let $cart = $('#selected-seats'), //Sitting Area
            $counter = $('#counter'), //Votes
            $total = $('#total'); //Total money

        let sc = $('#seat-map').seatCharts({
            map: [  //Seating chart
                'aaaaaaaaaa',
                'aaaaaaaaaa',
                'aaaaaaaaaa',
                'aaaaaaaaaa',
                'aaaaaaaaaa',
                'aaaaaaaaaa',
                'aaaaaaaaaa',
                'aaaaaaaaaa',
                'aaaaaaaaaa',
                'aaaaaaaaaa'
            ],
            naming: {
                top: false,
                getLabel: function (character, row, column) {
                    return column;
                }
            },
            legend: { //Definition legend
                node: $('#legend'),
                items: [
                    ['a', 'available', 'Свободно'],
                    ['a', 'unavailable', 'Продано']
                ]
            },
            click: function () { //Click event
                placeNumber = 0;
                if (this.status() == 'available') { //optional seat

                    placeNumber = placeNumber + this.settings.row;

                    $('<li>R' + (this.settings.row + 1) + ' S' + this.settings.label + '</li>')
                        .attr('id', 'cart-item-' + this.settings.id)
                        .data('seatId', this.settings.id)
                        .appendTo($cart);

                    let r1 = 10 * (this.settings.row);
                    let r2 = this.settings.label;

                    placeNumber = r1 + r2;
                    console.log(placeNumber);

                    $counter.text(sc.find('selected').length + 1);
                    $total.text(recalculateTotal(sc, session) + session.price);

                    return 'selected';
                } else if (this.status() == 'selected') { //Checked
                    // ********* Снимаем выбор ***************** //
                    //Update Number
                    $counter.text(sc.find('selected').length - 1);
                    //update totalnum
                    $total.text(recalculateTotal(sc, session) - session.price);

                    //Delete reservation
                    $('#cart-item-' + this.settings.id).remove();
                    //optional
                    return 'available';
                } else if (this.status() == 'unavailable') { //sold
                    return 'unavailable';
                } else {
                    return this.style();
                }
            }
        });
        //sold seat
        sc.get(getUnavailable(session.arrMap)).status('unavailable');
    }


    function drawInfo(session) {
        $('.film-name').text(session.filmName);
        $('.film-date').text(new Date(session.filmDate).toLocaleString());
        $('title').text('Фильм ' + session.filmName + '. Сеанс на: ' + new Date(session.filmDate).toLocaleString())
    }

    $('.pay-form .submit').on('click', function () {

        // FixMe убрать эти костыли
        let obj = s1.map;
        let arr = [];
        for (var key in obj) {
            if (obj.hasOwnProperty(key)) {
                arr.push(key);
            }
        }

        let clientOrder = {
            email: $('#email-input').val(),
            seansId: seansId,
            placeId: arr[placeNumber]
        };


        $.ajax({
            url: '/createOrder',
            method: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(clientOrder),
            success: function (response) {
                $('#liq-pay-data').val(response.data);
                $('#liq-pay-signature').val(response.signature);
                $('form').submit();
            }
        });
        event.preventDefault();
        return false;
    });


    // calculate total money
    function recalculateTotal(sc, session) {
        let total = 0;
        sc.find('selected').each(function () {
            total += session.price;
        });

        return total;
    }


})();