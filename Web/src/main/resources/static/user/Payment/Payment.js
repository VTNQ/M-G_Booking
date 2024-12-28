let totalpricePopup1 = document.getElementById('totalPrice');
let selectedPassenger = null;
let selectedSeat = null;
let selectedSeatName=null;
let totalPrice=0;

function createSeatDiv(seat) {

    const seatDiv = document.createElement('div');
    seatDiv.style.width = '50px';
    seatDiv.style.height = '50px';
    seatDiv.style.borderRadius = '4px';
    seatDiv.style.backgroundColor =
        seat.type === 'First Class' ? 'rgb(189, 211, 231)' :
            seat.type === 'Business Class' ? 'rgb(89, 144, 194)' :
                seat.type === 'Economy Class' ? '#2c4aff' : '#dcdcdc';
    seatDiv.title = `Seat ID: ${seat.id}`;
    seatDiv.dataset.seatId = seat.id;
    seatDiv.dataset.seatName = seat.index; // Add seat name for easier reference

    // Add event listener for selecting/deselecting seats
    seatDiv.addEventListener('click', () => {
        console.log(selectedPassenger)

        const seatName = seatDiv.dataset.seatName;
        if (seatDiv.classList.contains('isSelected')) {
            // Deselect the seat
            seatDiv.classList.remove('isSelected');
            seatDiv.querySelector('.fa-user')?.remove(); // Remove icon if deselected

            // Update passenger seat display
            selectedPassenger.querySelector('.selected-seat span').textContent = '';
        } else {
            // Check if the passenger already has a seat assigned
            const alreadyAssignedSeat = selectedPassenger.querySelector('.selected-seat span').textContent;

            // Select the seat
            seatDiv.classList.add('isSelected');
            const userIcon = document.createElement('i');
            userIcon.classList.add('fa', 'fa-user');
            userIcon.style.color = 'white'; // Font Awesome icon for selected
            seatDiv.appendChild(userIcon);
            const selectedPassengerSpan = document.querySelector('.passenger-block .passenger-type .selected-seat.selected');
            if (selectedPassengerSpan) {
                console.log(selectedPassengerSpan.textContent);
            } else {
                console.log('No selected passenger span found');
            }
            // Update passenger seat display
            document.querySelector('.passenger-block .passenger-type .selected-seat.selected').textContent = seatName;
        }
    });

    return seatDiv;
}

// Helper function to sanitize class names (replace spaces with hyphens)
function sanitizeClassName(name) {
    return name.replace(/\s+/g, '-');  // Replace spaces with hyphens
}

function renderSeatDetails(data) {
    const seatGrid = document.querySelector('.seat-grid');

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

// Fetch seat data for flight ID 5 (for example)
totalpricePopup1.textContent=totalPrice+' USD';
const flightId=document.getElementById('flightId');
fetchSeatData(flightId.value);
document.querySelector('.edit-button').addEventListener('click',function (event){
let popup=document.querySelector('.popup');
popup.classList.add('show');
});
document.querySelector('.confirm-button').addEventListener('click',function (event){
    let popup=document.querySelector('.popup');
    document.querySelector('.flight-info').style.display='flex';
    document.querySelector('.passengers').textContent=selectedSeatName;
    document.querySelector('.note').style.display='flex';
    document.querySelector('.price').style.display='flex'
    document.querySelector('.price').textContent='+ '+totalPrice+' USD';
    document.getElementById('amount').value+=totalPrice;
    popup.classList.remove('show');
})
document.querySelectorAll('.passenger-info').forEach((passenger, index) => {
    passenger.addEventListener('click', () => {

        document.querySelectorAll('.passenger-info').forEach(p => p.classList.remove('selected'));
        document.querySelectorAll('.passenger-info span').forEach(p=>p.classList.remove('selected'));

        passenger.classList.add('selected');
        const span = passenger.querySelector('.passenger-block .passenger-type .selected-seat')

        if (span) {
            span.classList.add('selected');
            console.log(span)
        }
    });
});
const firstPassenger = document.querySelector('.passenger-info');
const spanFirstPssager=document.querySelector('.passenger-info span');
if (firstPassenger) {
    firstPassenger.classList.add('selected');
    spanFirstPssager.classList.add('selected');
    selectedPassenger=firstPassenger;

}