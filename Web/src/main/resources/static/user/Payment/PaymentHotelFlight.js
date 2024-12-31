
let selectedPassenger = null;
let selectedSeat = null;
let selectedSeatName=null;
let totalPrice=0;
const selectedSeats = new Map();
function addSeat(seatId,SeatName){
    if (selectedSeats.has(seatId)) {
        // Nếu Map đã có seatId, kiểm tra xem seatName đã tồn tại chưa
        if (!selectedSeats.get(seatId).includes(SeatName)) {
            selectedSeats.get(seatId).push(SeatName); // Thêm seatName vào mảng nếu chưa có
        }
    } else {
        // Nếu Map chưa có seatId, khởi tạo mảng với seatName
        selectedSeats.set(seatId, [SeatName]);
    }

}
function openTab(event,tabId){
    const tabs = document.querySelectorAll(".tab-content");
    tabs.forEach(tab => tab.style.display = "none");

    // Remove active class from all tab links
    const links = document.querySelectorAll(".tab-link");
    links.forEach(link => link.classList.remove("active"));

    // Show the selected tab and set it as active
    document.getElementById(tabId).style.display = "block";
    event.currentTarget.classList.add("active");
}
document.addEventListener('DOMContentLoaded',function (){
    const timeoutSeconds=parseInt(document.getElementById('timeout').value);
    const CountDownElement=document.getElementById('countdown');
    let remainingTime=timeoutSeconds;
    function updateCountDown(){
        const minutes=Math.floor(remainingTime/60);
        const seconds=remainingTime%60;
        CountDownElement.textContent = `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
        if(remainingTime<=0){
            setTimeout(()=>{
                window.location.reload();
            },100)
        }else{
            remainingTime -= 1;
            setTimeout(updateCountDown, 1000);
        }
    }
    updateCountDown();
})
let bookings=[];
const maxSeats = 2;
function removeSeat(seatId){
    if(selectedSeats.has(seatId)){
        selectedSeats.delete(seatId);
    }
}
function createSeatDiv(seat) {

    const seatDiv = document.createElement('div');
    seatDiv.style.width = '50px';
    seatDiv.style.height = '50px';
    seatDiv.style.borderRadius = '4px';

    if(seat.status===1){
        console.log('test')
        seatDiv.style.backgroundColor='rgb(233, 235, 238)';
        seatDiv.style.cursor='not-allowed';
        const closeIcon = document.createElement('i');
        closeIcon.className = 'fa fa-close';
        seatDiv.style.display='flex';
        seatDiv.style.justifyContent='center';
        seatDiv.style.alignItems='center'
        seatDiv.appendChild(closeIcon);
    }else{
        seatDiv.style.backgroundColor =
            seat.type === 'First Class' ? 'rgb(189, 211, 231)' :
                seat.type === 'Business Class' ? 'rgb(89, 144, 194)' :
                    seat.type === 'Economy Class' ? '#2c4aff' : '#dcdcdc';
        seatDiv.title = `Seat ID: ${seat.id}`;
        seatDiv.dataset.seatId = seat.id;
        seatDiv.dataset.seatName = seat.index;
    }
    // Add seat name for easier reference
    if(seat.status!==1){
        seatDiv.addEventListener('click', () => {

            const hiddenInput = document.getElementById('bookings');
            const exists = bookings.some(booking => booking.id === seat.id);
            if(exists){
                const index = bookings.findIndex(booking => booking.id === seat.id);

                if (index !== -1) {
                    bookings.splice(index, 1);
                    hiddenInput.value=JSON.stringify(bookings);

                }
            }
            const seatName = seatDiv.dataset.seatName;
            if (seatDiv.classList.contains('isSelected')) {
                // Deselect the seat
                totalPrice=totalPrice-seat.price;
                if (totalPrice < 0) {
                    totalPrice = 0;
                }
                document.getElementById('totalPrice').textContent=totalPrice+' USD';
                seatDiv.classList.remove('isSelected');

                document.getElementById('totalPrice').textContent=totalPrice+' USD';
                seatDiv.querySelector('.fa-user')?.remove();
                const PassagerInfo=document.querySelectorAll('.passenger-info');
                console.log(PassagerInfo)
                PassagerInfo.forEach(pass=>{
                    console.log(pass.dataset.seat1Id);
                    if(pass.dataset.seat1Id===seat.id.toString()){
                        pass.classList.add('selected')
                        pass.querySelector('.passenger-block .seat-info .seat-price').textContent='0 USD'
                    }else{
                        pass.classList.remove('selected')
                    }
                })
                const flightId = document.getElementById('idFlight').value;


                const flightIdArray = JSON.parse(flightId);

                const seatIdElements = document.querySelectorAll(`.passenger-block .passenger-type .selected-seat`);
                seatIdElements.forEach(seatElement => {
                    // Check if this seat element has a matching seat ID

                    if (seatElement.dataset.seatId=== seat.id.toString() ) {
                        seatElement.textContent='';

                        delete seatElement.dataset.seatId;
                        removeSeat(seat.id)
                        seatElement.classList.remove('IsSelected')
                        seatElement.classList.add('selected')


                    }

                });

            } else {
                addSeat(seat.idFlight,seat.index);
                const selectedSeatsCount = document.querySelectorAll(`.seat-grid${seat.idFlight} .isSelected`).length;
                console.log(selectedSeatsCount)
                if (selectedSeatsCount >= maxSeats) {
                    alert("Bạn chỉ có thể chọn tối đa " + maxSeats + " ghế.");
                    return;  // Nếu số ghế đã chọn đủ, không cho phép chọn thêm ghế mới
                }
                const exists = bookings.some(booking => booking.id === seat.id);
                if(!exists){
                    bookings.push({id:seat.id,totalPrice:seat.price,flightId:seat.idFlight});
                    hiddenInput.value=JSON.stringify(bookings);
                }

                // Check if the passenger already has a seat assigned
                totalPrice+=seat.price;
                document.getElementById('totalPrice').textContent=totalPrice+' USD';
                // Select the seat
                seatDiv.classList.add('isSelected');
                const userIcon = document.createElement('i');
                userIcon.classList.add('fa', 'fa-user');
                userIcon.style.color = 'white'; // Font Awesome icon for selected
                seatDiv.appendChild(userIcon);

                // Update passenger seat display
                const seatIdElements = document.querySelectorAll(`.passenger-block .passenger-type .selected-seat.selected`);

// Check if any of these elements already contain the desired seat ID
                let seatExists = false;
                seatIdElements.forEach(seatElement => {
                    // Check if this seat element has a matching seat ID

                    if (seatElement.dataset.seatId=== seat.id.toString()) {
                        seatExists = true; // Seat already exists
                    }

                });

                if (seatExists===false) {


                    const query = document.querySelector('.passenger-block .passenger-type .selected-seat.selected');
                    const queryText = document.querySelectorAll('.passenger-block .passenger-type .selected-seat ');
                    const passagerInfo = document.querySelectorAll('.passenger-info');
                    const PassageQuery=document.querySelector('.passenger-info.selected');
                    PassageQuery.setAttribute('data-seat1-id', seat.id);
// Tìm index của phần tử có class 'selected' trong passagerInfo
                    const Passager = Array.from(passagerInfo).findIndex(span => span.classList.contains('selected'));

// Tìm index của phần tử có class 'selected' trong queryText
                    const indexOfSelected = Array.from(queryText).findIndex(span => span.classList.contains('selected'));

// Kiểm tra điều kiện tồn tại và tránh lỗi
                    if (
                        Passager !== -1 && // Kiểm tra có phần tử selected trong passagerInfo
                        indexOfSelected !== -1 && // Kiểm tra có phần tử selected trong queryText
                        Passager + 1 < passagerInfo.length && // Đảm bảo không vượt quá số lượng phần tử trong passagerInfo
                        indexOfSelected + 1 < queryText.length // Đảm bảo không vượt quá số lượng phần tử trong queryText
                    ) {

                        // Thêm class 'selected' vào phần tử kế tiếp
                        passagerInfo[Passager + 1].classList.add('selected');
                        queryText[indexOfSelected + 1].classList.add('selected');
                    } else {


                    }
                    const isLast = Passager === passagerInfo.length - 1;
                    if(isLast){
                        passagerInfo[Passager].classList.remove('selected')
                        passagerInfo[0].classList.add('selected');
                    }else{

                        document.querySelector('.passenger-info.selected').classList.remove('selected');

                    }
                    passagerInfo[Passager].querySelector('.seat-price').textContent=seat.price+' USD';

                    query.textContent= seatName;
                    query.setAttribute('data-seat-id', seat.id);
                    query.classList.add('IsSelected');


                    query.classList.remove('selected')





                }


            }
        });

    }
    // Add event listener for selecting/deselecting seats

    return seatDiv;
}

// Helper function to sanitize class names (replace spaces with hyphens)
function sanitizeClassName(name) {
    return name.replace(/\s+/g, '-');  // Replace spaces with hyphens
}

function renderSeatDetails(data) {
    let seatGrid = null;

    for (let i = 0; i < data.length; i++) {
        const idFlight = data[i].idFlight; // Lấy idFlight từ từng phần tử
        seatGrid=document.querySelector(`.seat-grid${idFlight}`);


    }


    if (!seatGrid) {
        console.error("seatGrid element not found!");
        return;
    }

    seatGrid.innerHTML = ''; // Clear existing seat details

    // Add seat header (A, B, C, Row, D, E, F)
    const headerRow = document.createElement('div');
    headerRow.style.display = 'flex';
    headerRow.style.alignItems = 'center';
    headerRow.style.justifyContent = 'center';
    headerRow.style.marginBottom = '10px';
    headerRow.style.gap = '10px';

    const headers = ['','A', 'B', 'C', 'D', 'E', 'F'];
    headers.forEach(label => {
        const headerDiv = document.createElement('div');
        headerDiv.style.width = '50px';
        headerDiv.style.textAlign = 'center';
        headerDiv.style.fontWeight = 'bold';
        headerDiv.textContent = label;
        if (label === 'D') {
            headerDiv.style.marginLeft = '18px';
        }
        headerRow.appendChild(headerDiv);
    });

    seatGrid.appendChild(headerRow); // Append header to seatGrid

    // Create seat rows
    let currentRowNumber = 1; // Start from 1
    let rowDiv;

    data.forEach((seat, index) => {
        // Create a new row if it's the first seat or every 6 seats
        if (index % 6 === 0) {
            if (rowDiv) {
                seatGrid.appendChild(rowDiv);
            }

            rowDiv = document.createElement('div');
            rowDiv.style.display = 'flex';
            rowDiv.style.alignItems = 'center';
            rowDiv.style.marginBottom = '5px';
            rowDiv.style.gap = '10px';
            if (index % 6 === 3) {
                const emptyDiv = document.createElement('div');
                emptyDiv.style.width = '50px'; // Adjust width as needed
                rowDiv.appendChild(emptyDiv);
            }
            // Create and append the row number div at the beginning of the row
            const rowNumberDiv = document.createElement('div');
            rowNumberDiv.textContent = currentRowNumber;
            rowNumberDiv.style.width = '50px';
            rowNumberDiv.style.textAlign = 'center';
            rowNumberDiv.style.fontWeight = 'bold';
            rowDiv.appendChild(rowNumberDiv);

            currentRowNumber++; // Increment row number after creating the row
        }

        // Create seat element
        const seatDiv = createSeatDiv(seat);

        // Add seat to the current row
        rowDiv.appendChild(seatDiv);
        if (seat.index.charAt(0) === 'D' ) {
            seatDiv.style.marginLeft = '18px'; // Reduce margin between C and D
        }
    });

// Append the last row
    if (rowDiv) {
        seatGrid.appendChild(rowDiv);
    }}

let socketMap = {};
let isConnected = false;




// This function will keep calling WebSocket to get updates
async function fetchSeatData(id) {
    try {
        const response = await fetch(`http://localhost:8686/api/seat/${id}`);
        if (response.ok) {
            const seatData = await response.json();
            renderSeatDetails(seatData);
        } else {
            console.error('Error fetching seat data:', response.status);
        }
    } catch (error) {
        console.error('Request failed', error);
    }
}

const flightId = document.getElementById('idFlight').value;


const flightIdArray = JSON.parse(flightId);
for (let i = 0; i < flightIdArray.length; i++) {
    fetchSeatData(flightIdArray[i]);
}

document.querySelector('.edit-button').addEventListener('click',function (event){
    let popup=document.querySelector('.popup');
    popup.classList.add('show');
});
document.querySelector('.confirm-button').addEventListener('click',function (event){
    let popup=document.querySelector('.popup');
    document.querySelectorAll('.flight-info').forEach(function(element) {
        element.style.display = 'flex';
    })
    const seatNames=Array.from(selectedSeats.values());
    console.log(selectedSeats)
    document.querySelector('.passengers').textContent=seatNames.join(', ');
    for (let [key, value] of selectedSeats) {
        // Giả sử `value` là giá trị bạn muốn hiển thị cho hành khách
        const seatNames = Array.from(selectedSeats.entries())
            .filter(([seatKey, _]) => seatKey === key)  // Lọc các entries theo key
            .map(([_, seatName]) => seatName);

        document.querySelector(`#passagers${key}`).textContent = seatNames.join(',');
    }
    document.querySelector('.note').style.display='flex';
    document.querySelector('.price').style.display='flex'
    document.querySelector('.price').textContent='+ '+totalPrice+' USD';
    document.getElementById('totalPriceFlight').value+=totalPrice;
    document.getElementById('amount').value+=totalPrice;
    popup.classList.remove('show');
})
document.querySelectorAll('.passenger-info').forEach((passenger, index) => {
    passenger.addEventListener('click', () => {
        const flightId = document.getElementById('idFlight').value;
        // Deselect any previously selected passengers and their spans
        document.querySelectorAll(`.tab-passage`).forEach(p => p.classList.remove('selected'));
        document.querySelectorAll('.passenger-info span').forEach(p => p.classList.remove('selected'));

        passenger.classList.add('selected');
        selectedPassenger = passenger; // Update the selected passenger

        // If there is a selected seat already assigned, highlight it
        const span = passenger.querySelector('.passenger-block .passenger-type .selected-seat');
        if (span) {
            span.classList.add('selected');
        }
    });
});

const firstPassenger = document.querySelector('.passenger-info');
const spanFirstPassenger = document.querySelector('.selected-seat span');

if (firstPassenger) {
    firstPassenger.querySelector('.passenger-block .passenger-type .selected-seat').classList.add('selected');
    firstPassenger.classList.add('selected');
    spanFirstPassenger.classList.add('selected');


}