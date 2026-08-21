# Student Portal Implementation Plan

Implement a visually appealing student portal with a mock login screen, dashboard, and smooth animations using Jetpack Compose.

## User Review Required

- **Mock Login**: No real authentication logic. Any input (or specific mock credentials) will "log in".
- **UI Style**: Material 3 cards and layouts for a modern look.
- **Animations**: `AnimatedContent` for screen transitions and `animateFloatAsState` or similar for dashboard elements.

## Proposed Changes

### Data Model

#### [NEW] [Student.kt](file:///D:/Android Studio/app/src/main/java/com/example/studentmanagment/model/Student.kt)
Define data classes for `Student`, `Course`, `Grade`, and `Attendance`.

### UI Components

#### [NEW] [LoginScreen.kt](file:///D:/Android Studio/app/src/main/java/com/example/studentmanagment/ui/LoginScreen.kt)
- Student ID and Password fields.
- Login button with animation.
- Basic validation feedback (optional).

#### [NEW] [DashboardScreen.kt](file:///D:/Android Studio/app/src/main/java/com/example/studentmanagment/ui/DashboardScreen.kt)
- Displays student name.
- Sections for:
    - **Courses**: List of enrolled courses.
    - **Grades**: Current marks/GPA.
    - **Attendance**: Percentage or visual indicator.
- Use `ElevatedCard` for each section.

#### [MODIFY] [MainActivity.kt](file:///D:/Android Studio/app/src/main/java/com/example/studentmanagment/MainActivity.kt)
- Manage app state (LoggedIn vs. LoggedOut).
- Use `AnimatedContent` to switch between `LoginScreen` and `DashboardScreen`.

## Verification Plan

### Automated Tests
- None planned for this visual task, but unit tests for the login logic could be added if requested.

### Manual Verification
- Deploy to emulator/device.
- Verify login flow.
- Check dashboard layout and animations.
