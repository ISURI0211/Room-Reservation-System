requireAuth();
setActiveNav("reservation");

const toast = document.getElementById("toast");

function showToast(type, msg) {
  toast.style.display = "block";
  toast.className = `toast ${type}`;
  toast.textContent = msg;
}

function clearForm() {
  ["reservationNumber","guestName","address","contactNumber","checkInDate","checkOutDate"].forEach(id=>{
    document.getElementById(id).value = "";
  });
  document.getElementById("roomType").value = "SINGLE";
}

document.getElementById("btnClear").addEventListener("click", clearForm);

document.getElementById("btnSave").addEventListener("click", async () => {
  const payload = {
    reservationNumber: document.getElementById("reservationNumber").value.trim(),
    guestName: document.getElementById("guestName").value.trim(),
    address: document.getElementById("address").value.trim(),
    contactNumber: document.getElementById("contactNumber").value.trim(),
    roomType: document.getElementById("roomType").value,
    checkInDate: document.getElementById("checkInDate").value,
    checkOutDate: document.getElementById("checkOutDate").value
  };

  if (!payload.reservationNumber || !payload.guestName || !payload.address || !payload.contactNumber || !payload.checkInDate || !payload.checkOutDate) {
    showToast("error", "Please fill all fields.");
    return;
  }

  try {
    const saved = await apiFetch("/reservations", {
      method: "POST",
      body: JSON.stringify(payload)
    });
    showToast("success", `Saved! Reservation ID: ${saved.id} • Number: ${saved.reservationNumber}`);
    // keep number for quick viewing
    sessionStorage.setItem("lastReservationNumber", saved.reservationNumber);
  } catch (e) {
    showToast("error", e.message);
  }
});
