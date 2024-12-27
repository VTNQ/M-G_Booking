let totalpricePopup1 = document.getElementById('totalPrice');

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
    seatDiv.dataset.seatId = seat.id; seatDiv.addEventListener('click', () => {
        // Deselect the previously selected seat (if any)
        if (selectedSeat && selectedSeat !== seatDiv) {
            selectedSeat.classList.remove('isSelected');
            selectedSeat.querySelector('.fa-user')?.remove(); // Remove icon from previously selected seat
        }
        if(selectedSeatName && selectedSeatName!==seat.index){
            selectedSeatName=null;
            document.querySelector('.selected-seat').classList.remove('box-isselected');
        }

        // Toggle the selected seat
        if (seatDiv.classList.contains('isSelected')) {
            seatDiv.classList.remove('isSelected');
            seatDiv.querySelector('.fa-user')?.remove(); // Remove icon if deselected
            selectedSeat = null; // Reset the selected seat
            selectedSeatName='';
            totalPrice = 0;
            totalpricePopup1.textContent=totalPrice;
            document.querySelector('.selected-seat').textContent=selectedSeatName;
            document.querySelector('.selected-seat').classList.remove('box-isselected');
        } else {
            seatDiv.classList.add('isSelected');
            const userIcon = document.createElement('i');

            userIcon.classList.add('fa', 'fa-user');
            userIcon.style.color='white';// Font Awesome icon for selected
            seatDiv.appendChild(userIcon);
            selectedSeat = seatDiv; // Set the new selected seat
            selectedSeatName=seat.index;
            document.getElementById('seatId').value=seat.id;
            document.querySelector('.selected-seat').textContent=selectedSeatName;
            document.querySelector('.selected-seat').classList.add('box-isselected');
            totalPrice=seat.price;
            totalpricePopup1.textContent=totalPrice;
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
fetchSeatData(6);
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
