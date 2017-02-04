let session = {
    price: 10,
    filmName: 'Name',
    filmDate: '01.01.2017',
    map: [true, false, true, true, true, false, true, true, true, false, false, false, true, false, true, true, true, false, true, false]
};


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


let price = 10; //price
$(document).ready(function () {
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
                ['a', 'available', 'Option'],
                ['a', 'unavailable', 'Sold']
            ]
        },
        click: function () { //Click event
            if (this.status() == 'available') { //optional seat
                $('<li>R' + (this.settings.row + 1) + ' S' + this.settings.label + '</li>')
                    .attr('id', 'cart-item-' + this.settings.id)
                    .data('seatId', this.settings.id)
                    .appendTo($cart);

                $counter.text(sc.find('selected').length + 1);
                $total.text(recalculateTotal(sc) + price);

                return 'selected';
            } else if (this.status() == 'selected') { //Checked
                // ********* Снимаем выбор ***************** //
                //Update Number
                $counter.text(sc.find('selected').length - 1);
                //update totalnum
                $total.text(recalculateTotal(sc) - price);

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
    sc.get(getUnavailable(session.map)).status('unavailable');

});
//sum total money
function recalculateTotal(sc) {
    let total = 0;
    sc.find('selected').each(function () {
        total += price;
    });

    return total;
}