document.getElementById("from-input").addEventListener('input',async (event)=>{
    const search = event.target.value;
    const dropdown = document.getElementById("from-dropdown");
    const airportList = document.getElementById("airport-list-from");

    if (search.trim() === "") {
        dropdown.style.display = "none";
        return;
    }
    try {
        const response=await fetch(`http://localhost:8686/AirPort/SearchAirPort?search=${encodeURIComponent(search)}`);
        console.log(response)
        if (response.ok) {
            const airports = await response.json();
            airportList.innerHTML = "";

            airports.forEach(airport => {
                const li = document.createElement("li");

                // Set the dropdown header with the country of the airport
                li.innerHTML = `
                    <div class="dropdown-header">
                        <span id="city-name">${airport.country}</span>
                        <span id="all-airports">Mọi sân bay</span>
                    </div>
                `;

                // Iterate over the airportDTOS and append each airport item
                airport.aiportDTOS.forEach(airportdto => {
                    const airportItem = document.createElement("div");
                    airportItem.classList.add("airport-item");
                    airportItem.innerHTML = `
                        <div class="airport-info">
                            <span class="airport-name"><i class="fa fa-plane icon" style="margin-right: 8px; color: #2a2a2a;"></i>Sân bay ${airportdto.name}</span>
                            <span class="airport-code">${airportdto.city.name}</span>
                        </div>
                    `;

                    // Add click event listener specifically to the 'airport-item' div
                    airportItem.addEventListener("click", () => {
                        document.getElementById("from-input").value = `${airportdto.name} (${airportdto.code})`;

                        // Store the actual airport.id in the hidden input
                        document.getElementById("from-input-id").value = airportdto.id;

                        dropdown.style.display = "none";
                    });

                    li.appendChild(airportItem);
                });

                airportList.appendChild(li);
            });

            dropdown.style.display = "block";
        }
    }catch (error){
        console.log(error)
    }
})
document.getElementById("to-input").addEventListener("focus", function () {
    this.select(); // Highlight all text when input is focused
});
document.getElementById("from-input").addEventListener("focus", function () {
    this.select(); // Highlight all text when input is focused
});
document.getElementById("to-input").addEventListener('input', async (event) => {
    const search = event.target.value;
    const dropdown = document.getElementById("to-dropdown");
    const airportList = document.getElementById("airport-list");

    if (search.trim() === "") {
        dropdown.style.display = "none";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8686/AirPort/SearchAirPort?search=${encodeURIComponent(search)}`);
        if (response.ok) {
            const airports = await response.json();
            airportList.innerHTML = "";

            airports.forEach(airport => {
                const li = document.createElement("li");

                // Set the dropdown header with the country of the airport
                li.innerHTML = `
                    <div class="dropdown-header">
                        <span id="city-name">${airport.country}</span>
                        <span id="all-airports">Mọi sân bay</span>
                    </div>
                `;

                // Iterate over the airportDTOS and append each airport item
                airport.aiportDTOS.forEach(airportdto => {
                    const airportItem = document.createElement("div");
                    airportItem.classList.add("airport-item");
                    airportItem.innerHTML = `
                        <div class="airport-info">
                            <span class="airport-name"><i class="fa fa-plane icon" style="margin-right: 8px; color: #2a2a2a;"></i>Sân bay ${airportdto.name}</span>
                            <span class="airport-code">${airportdto.code}</span>
                        </div>
                    `;

                    // Add click event listener specifically to the 'airport-item' div
                    airportItem.addEventListener("click", () => {
                        document.getElementById("to-input").value = `${airportdto.name} (${airportdto.code})`;
                        document.getElementById("to-input-id").value = airportdto.id;
                        dropdown.style.display = "none"; // Hide dropdown after selection
                    });

                    li.appendChild(airportItem);
                });

                airportList.appendChild(li);
            });

            dropdown.style.display = "block";
        }
    } catch (error) {
        console.error("Error fetching airport data:", error);
    }
});

// Đóng dropdown khi nhấp ngoài
document.addEventListener("click", (event) => {
    const fromDropdown = document.getElementById("from-dropdown");
    const toDropdown = document.getElementById("to-dropdown");
    const searchFrom = document.getElementById("search-from");
    const searchTo = document.getElementById("search-to");

    // Ẩn dropdown "Bay từ" nếu nhấp ra ngoài
    if (!searchFrom.contains(event.target)) {
        fromDropdown.style.display = "none";
    }

    // Ẩn dropdown "Bay đến" nếu nhấp ra ngoài
    if (!searchTo.contains(event.target)) {
        toDropdown.style.display = "none";
    }
});
//Dropdown select




// Close dropdown if clicked outside

function initFlatpickr() {
    flatpickr("#datePickerInput", {
        mode: "range", // Enable date range selection
        dateFormat: "Y-m-d", // Format to match LocalDate (yyyy-MM-dd)
        defaultDate: [new Date()], // Set default dates in LocalDate format
        onReady: function (selectedDates, dateStr, instance) {
            if (selectedDates.length) {
                // Display only the first date in the range
                const firstDate = instance.formatDate(selectedDates[0], "Y-m-d");
                instance.element.value = firstDate; // Set the input value to the first date
            }
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (selectedDates.length) {
                // Update the input value to show only the first selected date
                const firstDate = instance.formatDate(selectedDates[0], "Y-m-d");
                instance.element.value = firstDate;
            }
        }
    });
}


document.getElementById("datePickerInput").addEventListener("focus", function () {
    // Initialize flatpickr only if not already initialized
    if (!this.classList.contains("flatpickr-input-active")) {
        initFlatpickr();
        this.classList.add("flatpickr-input-active");
    }
});
