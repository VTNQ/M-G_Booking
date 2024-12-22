const openModalBtns = document.querySelectorAll('.open-modal-btn');
const modal = document.getElementById('customModal');
const closeModalBtn = document.getElementById('closeModalBtn');
const cancelModalBtn = document.getElementById('cancelModalBtn');
const DetailSeat=document.querySelectorAll('#DetailSeat');
openModalBtns.forEach((btn) => {
    btn.addEventListener('click', function(event) {
        event.preventDefault(); // Ngừng việc chuyển hướng trang
        modal.style.display = 'flex'; // Hiển thị modal

        // Lấy id chuyến bay từ data-idFlight và truyền vào form nếu cần
        const flightId = this.getAttribute('data-idFlight');
        document.getElementById('idFlight').value=flightId;
    });
});
DetailSeat.forEach((btn) => {
    btn.addEventListener('click', function (event) {
        const idFlight = this.getAttribute('data-idFlight');
        console.log(idFlight);

        fetch(`http://localhost:8686/api/seat/${idFlight}`)
            .then(response => response.json())
            .then(data => {
                // Render and display seat data
                renderSeatDetails(data);

                // Show the modal (ensure you implement modal logic here if needed)
                document.getElementById('seatDetail').style.display = 'flex';
            })
            .catch(error => console.error('Error fetching seat details:', error));
    });
});

// Function to render seat details dynamically
function renderSeatDetails(data) {
    const seatGrid = document.querySelector('#seatDetail .seat-grid');

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

    const headers = ['A', 'B', 'C', '', 'D', 'E', 'F'];
    headers.forEach(label => {
        const headerDiv = document.createElement('div');
        headerDiv.style.width = '50px';
        headerDiv.style.textAlign = 'center';
        headerDiv.style.fontWeight = 'bold';
        headerDiv.textContent = label;
        headerRow.appendChild(headerDiv);
    });

    seatGrid.appendChild(headerRow); // Append header to seatGrid

    // Create seat rows
    let currentRowNumber = 0;
    let rowDiv; // Declare rowDiv outside the loop
    data.forEach((seat, index) => {
        // Create a new row if it's the first seat or every 6 seats (excluding row number)
        if (index % 6 === 0) {
            if (rowDiv) {
                seatGrid.appendChild(rowDiv); // Append the previous row before creating a new one
            }

            rowDiv = document.createElement('div'); // Create a new row
            rowDiv.style.display = 'flex';
            rowDiv.style.alignItems = 'center';
            rowDiv.style.justifyContent = 'center';
            rowDiv.style.marginBottom = '5px';
            rowDiv.style.gap = '10px';

            currentRowNumber++;
        }

        // Add row number in the middle (between C and D)
        if (index % 6 === 3) {
            const rowNumberDiv = document.createElement('div');
            rowNumberDiv.style.width = '50px';
            rowNumberDiv.style.textAlign = 'center';
            rowNumberDiv.style.fontWeight = 'bold';
            rowNumberDiv.textContent = currentRowNumber;
            rowDiv.appendChild(rowNumberDiv);
        }

        // Create seat element
        const seatDiv = createSeatDiv(seat);

        // Add seat to the current row
        rowDiv.appendChild(seatDiv);
    });

    // Append the last row
    if (rowDiv) {
        seatGrid.appendChild(rowDiv);
    }
}

// Helper function to create a seat div
function createSeatDiv(seat) {
    const seatDiv = document.createElement('div');
    seatDiv.style.width = '50px';
    seatDiv.style.height = '50px';
    seatDiv.style.borderRadius = '4px';
    seatDiv.style.backgroundColor =
        seat.type === 'First Class' ? '#a7d2ff' :
            seat.type === 'Business Class' ? '#7199ff' :
                seat.type === 'Economy Class' ? '#2c4aff' : '#dcdcdc';
    seatDiv.title = `Seat ID: ${seat.id}`;
    seatDiv.dataset.seatId = seat.id;
    return seatDiv;
}
