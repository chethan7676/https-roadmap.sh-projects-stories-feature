// Initialize stories from localStorage
function loadStories() {
  const storiesList = document.querySelector('.stories-list');
  storiesList.innerHTML = ''; // Clear existing stories
  
  // Retrieve stories from localStorage
  const stories = JSON.parse(localStorage.getItem('stories')) || [];

  const currentTime = Date.now();

  // Filter out expired stories
  const validStories = stories.filter(story => currentTime - story.timestamp < 24 * 60 * 60 * 1000); // 24 hours in ms

  // Update localStorage with the filtered stories
  localStorage.setItem('stories', JSON.stringify(validStories));

  // Render valid stories
  validStories.forEach(story => {
    const storyElement = document.createElement('div');
    storyElement.classList.add('story');
    storyElement.style.backgroundImage = `url(${story.image})`;
    storiesList.appendChild(storyElement);
  });
}

// Handle the image upload and conversion to Base64
function handleImageUpload(event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onloadend = function () {
      const base64Image = reader.result;
      const timestamp = Date.now(); // Store timestamp for expiration logic

      // Retrieve existing stories from localStorage
      const stories = JSON.parse(localStorage.getItem('stories')) || [];

      // Add new story to the list
      stories.push({ image: base64Image, timestamp });

      // Save the updated list back to localStorage
      localStorage.setItem('stories', JSON.stringify(stories));

      // Reload the stories
      loadStories();
    };
    reader.readAsDataURL(file); // Convert image to base64
  }
}

// Trigger file input for image upload
function uploadStory() {
  document.getElementById('file-input').click();
}

// Initialize the page by loading stories
window.onload = loadStories;
