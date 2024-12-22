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

    let rowDiv; // Declare rowDiv outside the loop
    data.forEach((seat, index) => {
        // Create a new row if it's the first seat or every 4 seats
        if (index % 4 === 0) {
            rowDiv = document.createElement('div'); // Create a new row
            rowDiv.style.display = 'flex';
            rowDiv.style.alignItems = 'center';
            rowDiv.style.justifyContent = 'center';
            rowDiv.style.marginBottom = '5px';
            rowDiv.style.gap = '10px';
            seatGrid.appendChild(rowDiv); // Append row to seatGrid
        }

        // Create seat element
        const seatDiv = createSeatDiv(seat);

        // Add seat to the current row
        rowDiv.appendChild(seatDiv);
    });
}

// Helper function to create a seat div
function createSeatDiv(seat) {
    const seatDiv = document.createElement('div');
    seatDiv.style.width = '30px';
    seatDiv.style.height = '30px';
    seatDiv.style.borderRadius = '4px';
    seatDiv.style.backgroundColor = seat.color || '#2c4aff'; // Use seat color if available
    seatDiv.title = `Seat ID: ${seat.id}`;
    seatDiv.dataset.seatId = seat.id;

    return seatDiv;
}
