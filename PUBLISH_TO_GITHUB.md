# Publish To GitHub

This guide helps you publish this portfolio as a public GitHub repository and GitHub Pages website.

## 1. Create Your GitHub Account

1. Go to `https://github.com/signup`
2. Create your account
3. Choose a professional username such as:
   - `sangramshinde`
   - `sangram-qa`
   - `sangram-testing`

Recommended:

- use your real name if available
- add a profile photo
- set your display name to `Sangram Shinde`
- add bio:

`Software Test Engineer | Selenium | Playwright | API Testing | SQL | Jenkins`

## 2. Create The Repository

Selected repository name:

- `sangram-qa-portfolio`

Recommended settings:

- visibility: `Public`
- initialize with README: `No`
- add `.gitignore`: `No`
- choose a license: optional

## 3. Upload This Portfolio

If you want to upload from the GitHub website:

1. Open your new repository
2. Click `uploading an existing file`
3. Open this local folder:

`C:\Users\HP\OneDrive\Documents\New project\github_qa_portfolio`

4. Drag all files and folders from that folder into GitHub
5. Commit with message:

`Add QA automation portfolio website and case studies`

## 4. Or Upload Using Git Commands

Open terminal in:

`C:\Users\HP\OneDrive\Documents\New project\github_qa_portfolio`

Run these commands:

```powershell
git init
git add .
git commit -m "Add QA automation portfolio website and case studies"
git branch -M main
git remote add origin https://github.com/Sangram231116/sangram-qa-portfolio.git
git push -u origin main
```

## 5. Enable GitHub Pages

1. Open the repository on GitHub
2. Go to `Settings`
3. Open `Pages`
4. Under `Build and deployment` choose:
   - Source: `Deploy from a branch`
   - Branch: `main`
   - Folder: `/ (root)`
5. Click `Save`

Your live site will usually be:

`https://sangram231116.github.io/sangram-qa-portfolio/`

## 6. Add This Link To Upwork

Use both links in your profile:

- GitHub repository:
  `https://github.com/Sangram231116/sangram-qa-portfolio`
- Live portfolio:
  `https://sangram231116.github.io/sangram-qa-portfolio/`

## 7. Optional GitHub Profile README

If you want a profile page too:

1. Create a new public repository with the exact same name as your username
2. Example:
   - username: `Sangram231116`
   - repo name: `Sangram231116`
3. Copy content from:

`GITHUB_PROFILE_TEMPLATE.md`

4. Paste it into that repository's `README.md`

## 8. Suggested First Public Profile Setup

Add these details to GitHub profile:

- Name: `Sangram Shinde`
- Bio: `Software Test Engineer | Automation Test Engineer`
- Location: `Pune, India`
- Email: your preferred professional email

## 9. Good Practice

- keep client names generalized if needed
- do not upload internal code or confidential screenshots
- only publish public-safe artifacts and summaries
