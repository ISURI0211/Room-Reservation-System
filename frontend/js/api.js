const API_BASE_URL = "http://localhost:8080";

function getToken() {
  return sessionStorage.getItem("token");
}

function isLoggedIn() {
  return sessionStorage.getItem("loggedIn") === "true" && !!getToken();
}

function requireAuth() {
  if (!isLoggedIn()) {
    window.location.href = "login.html";
  }
}

async function apiFetch(path, options = {}) {
  const headers = options.headers ? { ...options.headers } : {};

  // JSON default
  if (!headers["Content-Type"] && options.body) {
    headers["Content-Type"] = "application/json";
  }

  // Attach token if exists
  const token = getToken();
  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(`${API_BASE_URL}${path}`, { ...options, headers });

  // Try parse json safely
  const contentType = res.headers.get("content-type") || "";
  const isJson = contentType.includes("application/json");
  const data = isJson ? await res.json().catch(() => null) : await res.text().catch(() => null);

  if (!res.ok) {
    // Your backend returns ApiError; show message cleanly
    const msg =
      (data && data.message) ? data.message :
      (typeof data === "string" && data) ? data :
      `Request failed (${res.status})`;

    const details = (data && data.validationErrors) ? data.validationErrors.join("\n") : "";
    const err = new Error(details ? `${msg}\n${details}` : msg);
    err.status = res.status;
    err.data = data;
    throw err;
  }

  return data;
}

function logout() {
  sessionStorage.removeItem("loggedIn");
  sessionStorage.removeItem("token");
  sessionStorage.removeItem("role");
  window.location.href = "login.html";
}

function setActiveNav(pageName) {
  const links = document.querySelectorAll(".nav-links a");
  links.forEach(a => {
    if (a.getAttribute("data-page") === pageName) a.classList.add("active");
  });
}
