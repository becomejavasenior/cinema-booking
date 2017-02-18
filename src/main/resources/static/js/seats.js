(function () {

    let locationPathName = window.location.pathname;
    let n = locationPathName.lastIndexOf('/');
    let filmId = locationPathName.substring(n + 1);

    let session = getSession(filmId);


    function getSession(filmId) {
        let session = {};

        $.ajax({
            url: '/getSeats/' + filmId,
            method: 'GET',
            success: function (response) {
                session.price = response.price;
                session.filmId = filmId;
                session.filmName = response.filmName;
                session.filmDate = response.filmDate;
                session.map = response.map;

                session.arrMap = [];
                Object.values(session.map).forEach(value => session.arrMap.push(value));

                console.log('Результат запроса');
                console.log(session);

                drawInfo(session);
                drawSeats(session);
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
                if (this.status() == 'available') { //optional seat
                    $('<li>R' + (this.settings.row + 1) + ' S' + this.settings.label + '</li>')
                        .attr('id', 'cart-item-' + this.settings.id)
                        .data('seatId', this.settings.id)
                        .appendTo($cart);

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
        $('.film-date').text(new Date(session.filmDate).toLocaleDateString())
    }

    $('.pay-form .submit').on('click', function () {

        // ToDo объединить запрос на создание order и получение данных для LiqPay в один запрос
        // FixMe переименовать поле email на id заказа

        $.ajax({
            url: '/api/rest/liqpay/account/getLiqPayParam',
            method: 'POST',
            data: {'email': $('#email-input').val(), 'amount': 80},
            success: function (response) {
                $('#liq-pay-data').val(response[0]);
                $('#liq-pay-signature').val(response[1]);
                $('form').submit();
            }
        });
        return false;
    });

//sum total money
    function recalculateTotal(sc, session) {
        let total = 0;
        sc.find('selected').each(function () {
            total += session.price;
        });

        return total;
    }
})();