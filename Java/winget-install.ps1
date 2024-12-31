<#PSScriptInfo
 
.VERSION 5.0.5
 
.GUID 3b581edb-5d90-4fa1-ba15-4f2377275463
 
.AUTHOR asheroto
 
.COMPANYNAME asheroto
 
.TAGS PowerShell Windows winget win get install installer fix script setup
 
.PROJECTURI https://github.com/asheroto/winget-install
 
.RELEASENOTES
[Version 0.0.1] - Initial Release.
[Version 0.0.2] - Implemented function to get the latest version of winget and its license.
[Version 0.0.3] - Signed file for PSGallery.
[Version 0.0.4] - Changed URI to grab latest release instead of releases and preleases.
[Version 0.0.5] - Updated version number of dependencies.
[Version 1.0.0] - Major refactor code, see release notes for more information.
[Version 1.0.1] - Fixed minor bug where version 2.8 was hardcoded in URL.
[Version 1.0.2] - Hardcoded UI Xaml version 2.8.4 as a failsafe in case the API fails. Added CheckForUpdates, Version, Help functions. Various bug fixes.
[Version 1.0.3] - Added error message to catch block. Fixed bug where appx package was not being installed.
[Version 1.0.4] - MisterZeus optimized code for readability.
[Version 2.0.0] - Major refactor. Reverted to UI.Xaml 2.7.3 for stability. Adjusted script to fix install issues due to winget changes (thank you ChrisTitusTech). Added in all architecture support.
[Version 2.0.1] - Renamed repo and URL references from winget-installer to winget-install. Added extra space after the last line of output.
[Version 2.0.2] - Adjusted CheckForUpdates to include Install-Script instructions and extra spacing.
[Version 2.1.0] - Added alternate method/URL for dependencies in case the main URL is down. Fixed licensing issue when winget is installed on Server 2022.
[Version 2.1.1] - Switched primary/alternate methods. Added Cleanup function to avoid errors when cleaning up temp files. Added output of URL for alternate method. Suppressed Add-AppxProvisionedPackage output. Improved success message. Improved verbiage. Improve PS script comments. Added check if the URL is empty. Moved display of URL beneath the check.
[Version 3.0.0] - Major changes. Added OS version detection checks - detects OS version, release ID, ensures compatibility. Forces older file installation for Server 2022 to avoid issues after installing. Added DebugMode, DisableCleanup, Force. Renamed CheckForUpdates to CheckForUpdate. Improved output. Improved error handling. Improved comments. Improved code readability. Moved CheckForUpdate into function. Added PowerShellGalleryName. Renamed Get-OSVersion to Get-OSInfo. Moved architecture detection into Get-OSInfo. Renamed Get-NewestLink to Get-WingetDownloadUrl. Have Get-WingetDownloadUrl not get preview releases.
[Version 3.0.1] - Updated Get-OSInfo function to fix issues when used on non-English systems. Improved error handling of "resources in use" error.
[Version 3.0.2] - Added winget registration command for Windows 10 machines.
[Version 3.1.0] - Added support for one-line installation with irm and iex compatible with $Force session variable. Added UpdateSelf command to automatically update the script to the latest version. Created short URL asheroto.com/winget.
[Version 3.1.1] - Changed winget register command to run on all OS versions.
[Version 3.2.0] - Added -ForceClose logic to relaunch the script in conhost.exe and automatically end active processes associated with winget that could interfere with the installation. Improved verbiage on winget already installed.
[Version 3.2.1] - Fixed minor glitch when using -Version or -Help parameters.
[Version 3.2.2] - Improved script exit functionality.
[Version 3.2.3] - Improved -ForceClose window handling with x86 PowerShell process.
[Version 3.2.4] - Improved verbiage for incompatible systems. Added importing Appx module on Windows Server with PowerShell 7+ systems to avoid error message.
[Version 3.2.5] - Removed pause after script completion. Added optional Wait parameter to force script to wait several seconds for script output.
[Version 3.2.6] - Improved ExitWithDelay function. Sometimes PowerShell will close the window accidentally, even when using the proper 'exit' command. Adjusted several closures for improved readability. Improved error code checking. Fixed glitch with -Wait param.
[Version 3.2.7] - Addded ability to install for all users. Added checks for Windows Sandbox and administrative privileges.
[Version 4.0.0] - Microsoft created some short URLs for winget. Removed a large portion of the script to use short URLs instead. Simplified and refactored. Switched debug param from DebugMode to Debug.
[Version 4.0.1] - Fixed PowerShell help information.
[Version 4.0.2] - Adjusted UpdateSelf function to reset PSGallery to original state if it was not trusted. Improved comments.
[Version 4.0.3] - Updated UI.Xaml package as per winget-cli issue #4208.
[Version 4.0.4] - Fixed detection for Windows multi-session.
[Version 4.0.5] - Improved error handling when registering winget.
[Version 4.1.0] - Support for Windows Server 2019 added by installing Visual C++ Redistributable.
[Version 4.1.1] - Minor revisions to comments & debug output.
[Version 4.1.2] - Implemented Visual C++ Redistributable version detection to ensure compatibility with winget.
[Version 4.1.3] - Added additional debug output for Visual C++ Redistributable version detection.
[Version 5.0.0] - Completely changed method to use winget-cli Repair-WingetPackageManager. Added environment path detection and addition if needed. Added NoExit parameter to prevent script from exiting after completion. Adjusted permissions of winget folder path for Server 2019. Improved exit handling to avoid PowerShell window closing.
[Version 5.0.1] - Fixed typo in variable name.
[Version 5.0.2] - Added detection of NuGet and PowerShell version to determine if package provider installation is needed.
[Version 5.0.3] - Fixed missing argument in call to Add-ToEnvironmentPath.
[Version 5.0.4] - Fixed bug with UpdateSelf function. Fixed bug when installing that may cause NuGet prompt to not be suppressed. Introduced Install-NuGetIfRequired function.
[Version 5.0.5] - Fixed exit code issue. Fixes #52.
 
#>

<#
.SYNOPSIS
    Downloads and installs the latest version of winget and its dependencies.
.DESCRIPTION
    Downloads and installs the latest version of winget and its dependencies.
 
This script is designed to be straightforward and easy to use, removing the hassle of manually downloading, installing, and configuring winget. This function should be run with administrative privileges.
.EXAMPLE
    winget-install
.PARAMETER Debug
    Enables debug mode, which shows additional information for debugging.
.PARAMETER Force
    Ensures installation of winget and its dependencies, even if already present.
.PARAMETER ForceClose
    Relaunches the script in conhost.exe and automatically ends active processes associated with winget that could interfere with the installation.
.PARAMETER Wait
    Forces the script to wait several seconds before exiting.
.PARAMETER NoExit
    Forces the script to wait indefinitely before exiting.
.PARAMETER UpdateSelf
    Updates the script to the latest version on PSGallery.
.PARAMETER CheckForUpdate
    Checks if there is an update available for the script.
.PARAMETER Version
    Displays the version of the script.
.PARAMETER Help
    Displays the full help information for the script.
.NOTES
    Version : 5.0.5
    Created by : asheroto
.LINK
    Project Site: https://github.com/asheroto/winget-install
#>
[CmdletBinding()]
param (
    [switch]$Force,
    [switch]$ForceClose,
    [switch]$CheckForUpdate,
    [switch]$Wait,
    [switch]$NoExit,
    [switch]$UpdateSelf,
    [switch]$Version,
    [switch]$Help
)

# Script information
$CurrentVersion = '5.0.5'
$RepoOwner = 'asheroto'
$RepoName = 'winget-install'
$PowerShellGalleryName = 'winget-install'

# Preferences
$ProgressPreference = 'SilentlyContinue' # Suppress progress bar (makes downloading super fast)
$ConfirmPreference = 'None' # Suppress confirmation prompts

# Display version if -Version is specified
if ($Version.IsPresent) {
    $CurrentVersion
    exit 0
}

# Display full help if -Help is specified
if ($Help) {
    Get-Help -Name $MyInvocation.MyCommand.Source -Full
    exit 0
}

# Display $PSVersionTable and Get-Host if -Verbose is specified
if ($PSBoundParameters.ContainsKey('Verbose') -and $PSBoundParameters['Verbose']) {
    $PSVersionTable
    Get-Host
}

# Set debug preferences if -Debug is specified
if ($PSBoundParameters.ContainsKey('Debug') -and $PSBoundParameters['Debug']) {
    $DebugPreference = 'Continue'
    $ConfirmPreference = 'None'
}

function Get-OSInfo {
    <#
        .SYNOPSIS
        Retrieves detailed information about the operating system version and architecture.
 
        .DESCRIPTION
        This function queries both the Windows registry and the Win32_OperatingSystem class to gather comprehensive information about the operating system. It returns details such as the release ID, display version, name, type (Workstation/Server), numeric version, edition ID, version (object that includes major, minor, and build numbers), and architecture (OS architecture, not processor architecture).
 
        .EXAMPLE
        Get-OSInfo
 
        This example retrieves the OS version details of the current system and returns an object with properties like ReleaseId, DisplayVersion, Name, Type, NumericVersion, EditionId, Version, and Architecture.
 
        .EXAMPLE
        (Get-OSInfo).Version.Major
 
        This example retrieves the major version number of the operating system. The Get-OSInfo function returns an object with a Version property, which itself is an object containing Major, Minor, and Build properties. You can access these sub-properties using dot notation.
 
        .EXAMPLE
        $osDetails = Get-OSInfo
        Write-Output "OS Name: $($osDetails.Name)"
        Write-Output "OS Type: $($osDetails.Type)"
        Write-Output "OS Architecture: $($osDetails.Architecture)"
 
        This example stores the result of Get-OSInfo in a variable and then accesses various properties to print details about the operating system.
    #>
    [CmdletBinding()]
    param ()

    try {
        # Get registry values
        $registryValues = Get-ItemProperty "HKLM:\SOFTWARE\Microsoft\Windows NT\CurrentVersion"
        $releaseIdValue = $registryValues.ReleaseId
        $displayVersionValue = $registryValues.DisplayVersion
        $nameValue = $registryValues.ProductName
        $editionIdValue = $registryValues.EditionId

        # Strip out "Server" from the $editionIdValue if it exists
        $editionIdValue = $editionIdValue -replace "Server", ""

        # Get OS details using Get-CimInstance because the registry key for Name is not always correct with Windows 11
        $osDetails = Get-CimInstance -ClassName Win32_OperatingSystem
        $nameValue = $osDetails.Caption

        # Get architecture details of the OS (not the processor)
        # Get only the numbers
        $architecture = ($osDetails.OSArchitecture -replace "[^\d]").Trim()

        # If 32-bit or 64-bit replace with x32 and x64
        if ($architecture -eq "32") {
            $architecture = "x32"
        } elseif ($architecture -eq "64") {
            $architecture = "x64"
        }

        # Get OS version details (as version object)
        $versionValue = [System.Environment]::OSVersion.Version

        # Determine product type
        # Reference: https://learn.microsoft.com/en-us/dotnet/api/microsoft.powershell.commands.producttype?view=powershellsdk-1.1.0
        if ($osDetails.ProductType -eq 1) {
            $typeValue = "Workstation"
        } elseif ($osDetails.ProductType -eq 2 -or $osDetails.ProductType -eq 3) {
            $typeValue = "Server"
        } else {
            $typeValue = "Unknown"
        }

        # Extract numerical value from Name
        $numericVersion = ($nameValue -replace "[^\d]").Trim()

        # If the numeric version is 10 or above, and the caption contains "multi-session", consider it a workstation
        if ($numericVersion -ge 10 -and $osDetails.Caption -match "multi-session") {
            $typeValue = "Workstation"
        }

        # Create and return custom object with the required properties
        $result = [PSCustomObject]@{
            ReleaseId      = $releaseIdValue
            DisplayVersion = $displayVersionValue
            Name           = $nameValue
            Type           = $typeValue
            NumericVersion = $numericVersion
            EditionId      = $editionIdValue
            Version        = $versionValue
            Architecture   = $architecture
        }

        return $result
    } catch {
        Write-Error "Unable to get OS version details.`nError: $_"
        ExitWithDelay 1
    }
}

function Get-GitHubRelease {
    <#
        .SYNOPSIS
        Fetches the latest release information of a GitHub repository.
 
        .DESCRIPTION
        This function uses the GitHub API to get information about the latest release of a specified repository, including its version and the date it was published.
 
        .PARAMETER Owner
        The GitHub username of the repository owner.
 
        .PARAMETER Repo
        The name of the repository.
 
        .EXAMPLE
        Get-GitHubRelease -Owner "asheroto" -Repo "winget-install"
        This command retrieves the latest release version and published datetime of the winget-install repository owned by asheroto.
    #>
    [CmdletBinding()]
    param (
        [string]$Owner,
        [string]$Repo
    )
    try {
        $url = "https://api.github.com/repos/$Owner/$Repo/releases/latest"
        $response = Invoke-RestMethod -Uri $url -ErrorAction Stop

        $latestVersion = $response.tag_name
        $publishedAt = $response.published_at

        # Convert UTC time string to local time
        $UtcDateTime = [DateTime]::Parse($publishedAt, [System.Globalization.CultureInfo]::InvariantCulture, [System.Globalization.DateTimeStyles]::RoundtripKind)
        $PublishedLocalDateTime = $UtcDateTime.ToLocalTime()

        [PSCustomObject]@{
            LatestVersion     = $latestVersion
            PublishedDateTime = $PublishedLocalDateTime
        }
    } catch {
        Write-Error "Unable to check for updates.`nError: $_"
        exit 1
    }
}

function CheckForUpdate {
    param (
        [string]$RepoOwner,
        [string]$RepoName,
        [version]$CurrentVersion,
        [string]$PowerShellGalleryName
    )

    $Data = Get-GitHubRelease -Owner $RepoOwner -Repo $RepoName

    Write-Output ""
    Write-Output ("Repository: {0,-40}" -f "https://github.com/$RepoOwner/$RepoName")
    Write-Output ("Current Version: {0,-40}" -f $CurrentVersion)
    Write-Output ("Latest Version: {0,-40}" -f $Data.LatestVersion)
    Write-Output ("Published at: {0,-40}" -f $Data.PublishedDateTime)

    if ($Data.LatestVersion -gt $CurrentVersion) {
        Write-Output ("Status: {0,-40}" -f "A new version is available.")
        Write-Output "`nOptions to update:"
        Write-Output "- Download latest release: https://github.com/$RepoOwner/$RepoName/releases"
        if ($PowerShellGalleryName) {
            Write-Output "- Run: $RepoName -UpdateSelf"
            Write-Output "- Run: Install-Script $PowerShellGalleryName -Force"
        }
    } else {
        Write-Output ("Status: {0,-40}" -f "Up to date.")
    }
    exit 0
}

function UpdateSelf {
    try {
        # Get PSGallery version of script
        $psGalleryScriptVersion = (Find-Script -Name $PowerShellGalleryName).Version

        # If the current version is less than the PSGallery version, update the script
        if ($CurrentVersion -lt $psGalleryScriptVersion) {
            Write-Output "Updating script to version $psGalleryScriptVersion..."

            # Check if running in PowerShell 7 or greater
            Write-Debug "Checking if NuGet PackageProvider is already installed..."
            Install-NuGetIfRequired

            # Trust the PSGallery if not already trusted
            $psRepoInstallationPolicy = (Get-PSRepository -Name 'PSGallery').InstallationPolicy
            if ($psRepoInstallationPolicy -ne 'Trusted') {
                Set-PSRepository -Name 'PSGallery' -InstallationPolicy Trusted | Out-Null
            }

            # Update the script
            Install-Script $PowerShellGalleryName -Force

            # If PSGallery was not trusted, reset it to its original state
            if ($psRepoInstallationPolicy -ne 'Trusted') {
                Set-PSRepository -Name 'PSGallery' -InstallationPolicy $psRepoInstallationPolicy | Out-Null
            }

            Write-Output "Script updated to version $psGalleryScriptVersion."
            exit 0
        } else {
            Write-Output "Script is already up to date."
            exit 0
        }
    } catch {
        Write-Output "An error occurred: $_"
        exit 1
    }
}

function Write-Section($text) {
    <#
        .SYNOPSIS
        Prints a text block surrounded by a section divider for enhanced output readability.
 
        .DESCRIPTION
        This function takes a string input and prints it to the console, surrounded by a section divider made of hash characters.
        It is designed to enhance the readability of console output.
 
        .PARAMETER text
        The text to be printed within the section divider.
 
        .EXAMPLE
        Write-Section "Downloading Files..."
        This command prints the text "Downloading Files..." surrounded by a section divider.
    #>
    Write-Output ""
    Write-Output ("#" * ($text.Length + 4))
    Write-Output "# $text #"
    Write-Output ("#" * ($text.Length + 4))
    Write-Output ""
}

function Get-WingetDownloadUrl {
    <#
        .SYNOPSIS
        Retrieves the download URL of the latest release asset that matches a specified pattern from the GitHub repository.
 
        .DESCRIPTION
        This function uses the GitHub API to get information about the latest release of the winget-cli repository.
        It then retrieves the download URL for the release asset that matches a specified pattern.
 
        .PARAMETER Match
        The pattern to match in the asset names.
 
        .EXAMPLE
        Get-WingetDownloadUrl "msixbundle"
        This command retrieves the download URL for the latest release asset with a name that contains "msixbundle".
    #>
    [CmdletBinding()]
    param (
        [Parameter(Mandatory = $true)]
        [string]$Match
    )

    $uri = "https://api.github.com/repos/microsoft/winget-cli/releases"
    $releases = Invoke-RestMethod -uri $uri -Method Get -ErrorAction Stop

    Write-Debug "Getting latest release..."
    foreach ($release in $releases) {
        if ($release.name -match "preview") {
            continue
        }
        $data = $release.assets | Where-Object name -Match $Match
        if ($data) {
            return $data.browser_download_url
        }
    }

    Write-Debug "Falling back to the latest release..."
    $latestRelease = $releases | Select-Object -First 1
    $data = $latestRelease.assets | Where-Object name -Match $Match
    return $data.browser_download_url
}

function Get-WingetStatus {
    <#
        .SYNOPSIS
        Checks if winget is installed.
 
        .DESCRIPTION
        This function checks if winget is installed.
 
        .EXAMPLE
        Get-WingetStatus
    #>

    # Check if winget is installed
    $winget = Get-Command -Name winget -ErrorAction SilentlyContinue

    # If winget is installed, return $true
    if ($null -ne $winget -and $winget -notlike '*failed to run*') {
        return $true
    }

    # If winget is not installed, return $false
    return $false
}

function Handle-Error {
    <#
        .SYNOPSIS
            Handles common errors that may occur during an installation process.
 
        .DESCRIPTION
            This function takes an ErrorRecord object and checks for certain known error codes.
            Depending on the error code, it writes appropriate warning messages or throws the error.
 
        .PARAMETER ErrorRecord
            The ErrorRecord object that represents the error that was caught. This object contains
            information about the error, including the exception that was thrown.
 
        .EXAMPLE
            try {
                # Some code that may throw an error...
            } catch {
                Handle-Error $_
            }
            This example shows how you might use the Handle-Error function in a try-catch block.
            If an error occurs in the try block, the catch block catches it and calls Handle-Error,
            passing the error (represented by the $_ variable) to the function.
    #>
    param($ErrorRecord)

    # Store current value
    $OriginalErrorActionPreference = $ErrorActionPreference

    # Set to silently continue
    $ErrorActionPreference = 'SilentlyContinue'

    # Handle common errors
    # Not returning $ErrorRecord on some errors is intentional
    if ($ErrorRecord.Exception.Message -match '0x80073D06') {
        Write-Warning "Higher version already installed."
        Write-Warning "That's okay, continuing..."
    } elseif ($ErrorRecord.Exception.Message -match '0x80073CF0') {
        Write-Warning "Same version already installed."
        Write-Warning "That's okay, continuing..."
    } elseif ($ErrorRecord.Exception.Message -match '0x80073D02') {
        # Stop execution and return the ErrorRecord so that the calling try/catch block throws the error
        Write-Warning "Resources modified are in-use. Try closing Windows Terminal / PowerShell / Command Prompt and try again."
        Write-Warning "Windows Terminal sometimes has trouble installing winget. If you are using Windows Terminal and the problem persists, run the script with the -ForceClose parameter which will relaunch the script in conhost.exe and automatically end active processes associated with winget that could interfere with the installation. Please note that using the -ForceClose parameter will close the PowerShell window and could break custom scripts that rely on the current PowerShell session."
        return $ErrorRecord
    } elseif ($ErrorRecord.Exception.Message -match '0x80073CF3') {
        # Prerequisite not detected, tell user to run it again
        Write-Warning "Problem with one of the prerequisites."
        Write-Warning "Try running the script again which usually fixes the issue. If the problem persists, try running the script with the -ForceClose parameter which will relaunch the script in conhost.exe and automatically end active processes associated with winget that could interfere with the installation. Please note that using the -ForceClose parameter will close the PowerShell window and could break custom scripts that rely on the current PowerShell session."
        return $ErrorRecord
    } elseif ($ErrorRecord.Exception.Message -match '0x80073CF9') {
        Write-Warning "Registering winget failed with error code 0x80073CF9."
        Write-Warning "This error usually occurs when using the Local System account to install winget. The SYSTEM account is not officially supported by winget and may not work. See the requirements section of the README. If winget is not working, run the installation script again using an Administrator account."
    } elseif ($ErrorRecord.Exception.Message -match 'Unable to connect to the remote server') {
        Write-Warning "Cannot connect to the Internet to download the required files."
        Write-Warning "Try running the script again and make sure you are connected to the Internet."
        Write-Warning "Sometimes the nuget.org server is down, so you may need to try again later."
        return $ErrorRecord
    } elseif ($ErrorRecord.Exception.Message -match "The remote name could not be resolved") {
        Write-Warning "Cannot connect to the Internet to download the required files."
        Write-Warning "Try running the script again and make sure you are connected to the Internet."
        Write-Warning "Make sure DNS is working correctly on your computer."
    } else {
        # For other errors, we should stop the execution and return the ErrorRecord so that the calling try/catch block throws the error
        return $ErrorRecord
    }

    # Reset to original value
    $ErrorActionPreference = $OriginalErrorActionPreference
}

function Get-CurrentProcess {
    <#
        .SYNOPSIS
            Retrieves the current PowerShell process information.
 
        .DESCRIPTION
            The Get-CurrentProcess function identifies the current PowerShell process by temporarily changing the console window title. It then filters the list of running processes to find the one with the matching window title. The function returns a custom object containing the Name and Id of the current process.
 
        .EXAMPLE
            PS C:\> $result = Get-CurrentProcess
            PS C:\> Write-Output $result
 
            This example demonstrates how to call the Get-CurrentProcess function and store its output in a variable named $result. The output is then displayed using Write-Output.
 
        .NOTES
            The function temporarily changes the console window title. Ensure no other scripts or processes are dependent on the window title during execution. The function uses a 1-second sleep to allow time for the window title change to take effect. This may vary based on system performance.
    #>
    $oldTitle = $host.ui.RawUI.WindowTitle
    $tempTitle = ([Guid]::NewGuid())
    $host.ui.RawUI.WindowTitle = $tempTitle
    Start-Sleep 1
    $currentProcess = Get-Process | Where-Object { $_.MainWindowTitle -eq $tempTitle }
    $currentProcess = [PSCustomObject]@{
        Name = $currentProcess.Name
        Id   = $currentProcess.Id
    }
    $host.ui.RawUI.WindowTitle = $oldTitle
    return $currentProcess
}

function ExitWithDelay {
    <#
        .SYNOPSIS
            Exits the script with a specified exit code after a specified delay, 10 seconds by default.
 
        .DESCRIPTION
            This function takes an exit code as an argument, waits for 10 seconds unless specified, and then exits the script with the given exit code.
 
        .PARAMETER ExitCode
            The exit code to use when exiting the script.
 
        .EXAMPLE
            ExitWithDelay -ExitCode 1
            Waits for 10 seconds (default) and then exits the script with an exit code of 1.
 
        .EXAMPLE
            ExitWithDelay -ExitCode 2 -Seconds 5
            Waits for 5 seconds and then exits the script with an exit code of 2.
        .NOTES
            Use this function to introduce a delay before exiting the script, allowing time for any cleanup or logging activities.
    #>

    param (
        [int]$ExitCode,
        [int]$Seconds = 10
    )

    # Debug mode output
    if ($Debug -and $Wait) {
        Write-Warning "Wait specified, waiting several seconds..."
    } elseif ($Debug -and !$Wait) {
        Write-Warning "Wait not specified, exiting immediately..."
    }

    # If Wait is specified, wait for x seconds before exiting
    if ($Wait) {
        # Waiting for x seconds output
        Write-Output "`nWaiting for $Seconds seconds before exiting..."
        Start-Sleep -Seconds $Seconds
    }

    # If NoExit is specified, do not exit the script
    if ($NoExit) {
        Write-Output "Script completed. Pausing indefinitely. Press any key to exit..."
        Read-Host
    }

    # Exit the script with exit code
    if ($MyInvocation.CommandOrigin -eq "Runspace") {
        Break
    } else {
        Exit $ExitCode
    }
}

function Import-GlobalVariable {
    <#
        .SYNOPSIS
        This function checks if a specified global variable exists and imports its value into a script scope variable of the same name.
 
        .DESCRIPTION
        The Import-GlobalVariable function allows you to specify the name of a variable. It checks if a global variable with that name exists, and if it does, it imports its value into a script scope variable with the same name.
 
        .PARAMETER VariableName
        The name of the variable to check and import if it exists in the global scope.
 
    #>

    [CmdletBinding()]
    param(
        [string]$VariableName
    )

    # Check if the specified global variable exists; if yes, import its value
    try {
        $globalValue = Get-Variable -Name $VariableName -ValueOnly -Scope Global -ErrorAction Stop
        Set-Variable -Name $VariableName -Value $globalValue -Scope Script
    } catch {
        # If the variable does not exist, do nothing
    }
}

function Test-AdminPrivileges {
    <#
    .SYNOPSIS
        Checks if the script is running with Administrator privileges. Returns $true if running with Administrator privileges, $false otherwise.
 
    .DESCRIPTION
        This function checks if the current PowerShell session is running with Administrator privileges by examining the role of the current user. It returns $true if the current user is an Administrator, $false otherwise.
 
    .EXAMPLE
        Test-AdminPrivileges
 
    .NOTES
        This function is particularly useful for scripts that require elevated permissions to run correctly.
    #>
    if (([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
        return $true
    }
    return $false
}

Function New-TemporaryFile2 {
    <#
    .SYNOPSIS
        Creates a new temporary file.
 
    .DESCRIPTION
        This function generates a temporary file and returns the full path of the file. The New-TemporaryFile command is not available in PowerShell 5.1 and earlier so this function is a workaround.
 
    .EXAMPLE
        $tempFile = New-TemporaryFile2
        This example creates a new temporary file and stores its path in the $tempFile variable.
 
    .OUTPUTS
        String. The full path of the created temporary file.
    #>

    # Create a temporary file
    $tempPath = [System.IO.Path]::GetTempPath()
    $tempFile = [System.IO.Path]::Combine($tempPath, [System.IO.Path]::GetRandomFileName())
    $null = New-Item -Path $tempFile -ItemType File -Force

    # Return the path of the temporary file
    return $tempFile
}

function Path-ExistsInEnvironment {
    param (
        [string]$PathToCheck,
        [string]$Scope = 'Both' # Valid values: 'User', 'System', 'Both'
    )
    <#
    .SYNOPSIS
    Checks if the specified path exists in the specified PATH environment variable.
 
    .DESCRIPTION
    This function checks if a given path is present in the user, system-wide, or both PATH environment variables.
 
    .PARAMETER PathToCheck
    The directory path to check in the environment PATH variable.
 
    .PARAMETER Scope
    The scope to check in the environment PATH variable. Valid values are 'User', 'System', or 'Both'. Default is 'Both'.
 
    .EXAMPLE
    Path-ExistsInEnvironment -PathToCheck "C:\Program Files\MyApp" -Scope 'User'
    #>

    $pathExists = $false

    if ($Scope -eq 'User' -or $Scope -eq 'Both') {
        $userEnvPath = $env:PATH
        if (($userEnvPath -split ';').Contains($PathToCheck)) {
            $pathExists = $true
        }
    }

    if ($Scope -eq 'System' -or $Scope -eq 'Both') {
        $systemEnvPath = [System.Environment]::GetEnvironmentVariable('PATH', [System.EnvironmentVariableTarget]::Machine)
        if (($systemEnvPath -split ';').Contains($PathToCheck)) {
            $pathExists = $true
        }
    }

    return $pathExists
}

function Add-ToEnvironmentPath {
    param (
        [Parameter(Mandatory = $true)]
        [string]$PathToAdd,

        [Parameter(Mandatory = $true)]
        [ValidateSet('User', 'System')]
        [string]$Scope
    )
    <#
    .SYNOPSIS
    Adds the specified path to the environment PATH variable.
 
    .DESCRIPTION
    This function adds a given path to the specified scope (user or system) and the current process environment PATH variable if it is not already present.
 
    .PARAMETER PathToAdd
    The directory path to add to the environment PATH variable.
 
    .PARAMETER Scope
    Specifies whether to add the path to the user or system environment PATH variable.
 
    .EXAMPLE
    Add-ToEnvironmentPath -PathToAdd "C:\Program Files\MyApp" -Scope 'System'
    #>

    # Check if the path is already in the environment PATH variable
    if (-not (Path-ExistsInEnvironment -PathToCheck $PathToAdd -Scope $Scope)) {
        if ($Scope -eq 'System') {
            # Get the current system PATH
            $systemEnvPath = [System.Environment]::GetEnvironmentVariable('PATH', [System.EnvironmentVariableTarget]::Machine)
            # Add to system PATH
            $systemEnvPath += ";$PathToAdd"
            [System.Environment]::SetEnvironmentVariable('PATH', $systemEnvPath, [System.EnvironmentVariableTarget]::Machine)
            Write-Debug "Adding $PathToAdd to the system PATH."
        } elseif ($Scope -eq 'User') {
            # Get the current user PATH
            $userEnvPath = [System.Environment]::GetEnvironmentVariable('PATH', [System.EnvironmentVariableTarget]::User)
            # Add to user PATH
            $userEnvPath += ";$PathToAdd"
            [System.Environment]::SetEnvironmentVariable('PATH', $userEnvPath, [System.EnvironmentVariableTarget]::User)
            Write-Debug "Adding $PathToAdd to the user PATH."
        }

        # Update the current process environment PATH
        if (-not ($env:PATH -split ';').Contains($PathToAdd)) {
            $env:PATH += ";$PathToAdd"
            Write-Debug "Adding $PathToAdd to the current process environment PATH."
        }
    } else {
        Write-Debug "$PathToAdd is already in the PATH."
    }
}

function Set-PathPermissions {
    param (
        [string]$FolderPath
    )
    <#
    .SYNOPSIS
    Grants full control permissions for the Administrators group on the specified directory path.
 
    .DESCRIPTION
    This function sets full control permissions for the Administrators group on the specified directory path.
    Useful for ensuring that administrators have unrestricted access to a given folder.
 
    .PARAMETER FolderPath
    The directory path for which to set full control permissions.
 
    .EXAMPLE
    Set-PathPermissions -FolderPath "C:\Program Files\MyApp"
 
    Sets full control permissions for the Administrators group on "C:\Program Files\MyApp".
    #>

    Write-Debug "Setting full control permissions for the Administrators group on $FolderPath."

    # Define the SID for the Administrators group
    $administratorsGroupSid = New-Object System.Security.Principal.SecurityIdentifier("S-1-5-32-544")
    $administratorsGroup = $administratorsGroupSid.Translate([System.Security.Principal.NTAccount])

    # Retrieve the current ACL for the folder
    $acl = Get-Acl -Path $FolderPath

    # Define the access rule for full control inheritance
    $accessRule = New-Object System.Security.AccessControl.FileSystemAccessRule(
        $administratorsGroup,
        "FullControl",
        "ContainerInherit,ObjectInherit",
        "None",
        "Allow"
    )

    # Apply the access rule to the ACL and set it on the folder
    $acl.SetAccessRule($accessRule)
    Set-Acl -Path $FolderPath -AclObject $acl
}

function Test-VCRedistInstalled {
    <#
    .SYNOPSIS
    Checks if Visual C++ Redistributable is installed and verifies the major version is 14.
 
    .DESCRIPTION
    This function checks the registry and the file system to determine if Visual C++ Redistributable is installed
    and if the major version is 14.
 
    .EXAMPLE
    Test-VCRedistInstalled
    #>

    # Assets
    $64BitOS = [System.Environment]::Is64BitOperatingSystem
    $64BitProcess = [System.Environment]::Is64BitProcess

    # Require running system native process
    if ($64BitOS -and -not $64BitProcess) {
        Throw 'Please run PowerShell in the system native architecture (x64 PowerShell if x64 Windows).'
    }

    # Check that uninstall information exists in the registry
    $registryPath = [string]::Format(
        'Registry::HKEY_LOCAL_MACHINE\SOFTWARE\{0}\Microsoft\VisualStudio\14.0\VC\Runtimes\X{1}',
        $(if ($64BitOS -and $64BitProcess) { 'WOW6432Node' } else { '' }),
        $(if ($64BitOS) { '64' } else { '86' })
    )

    $registryExists = Test-Path -Path $registryPath

    Write-Debug "Registry Path: $registryPath"
    Write-Debug "Registry Exists: $registryExists"

    # Check the major version
    $majorVersion = if ($registryExists) {
        (Get-ItemProperty -Path $registryPath -Name 'Major').Major
    } else {
        0
    }

    # Check the full version
    $version = if ($registryExists) {
        (Get-ItemProperty -Path $registryPath -Name 'Version').Version
    } else {
        0
    }

    Write-Debug "Major Version: $majorVersion"
    Write-Debug "Version: $version"

    # Check that one required DLL exists on the file system
    $dllPath = [string]::Format(
        '{0}\system32\concrt140.dll',
        $env:windir
    )

    $dllExists = [System.IO.File]::Exists($dllPath)

    Write-Debug "DLL Path: $dllPath"
    Write-Debug "DLL Exists: $dllExists"

    # Determine if VCRedist is installed and major version is 14
    return $registryExists -and $majorVersion -eq 14 -and $dllExists
}

function TryRemove {
    <#
    .SYNOPSIS
    Tries to remove a specified file if it exists.
 
    .DESCRIPTION
    This function checks if the specified file exists and attempts to remove it.
    It will not produce an error if the file does not exist or if the removal fails.
 
    .PARAMETER FilePath
    The path to the file that should be removed.
 
    .EXAMPLE
    TryRemove -FilePath "C:\path\to\file.txt"
    #>

    param (
        [Parameter(Mandatory = $true)]
        [string]$FilePath
    )

    try {
        if (Test-Path -Path $FilePath) {
            Remove-Item -Path $FilePath -ErrorAction SilentlyContinue
            if ($?) {
                Write-Debug "File '$FilePath' was successfully removed."
            } else {
                Write-Debug "Failed to remove the file '$FilePath'."
            }
        } else {
            Write-Debug "File '$FilePath' does not exist."
        }
    } catch {
        Write-Debug "An error occurred while trying to remove the file '$FilePath'."
    }
}

function Install-NuGetIfRequired {
    <#
    .SYNOPSIS
    Checks if the NuGet PackageProvider is installed and installs it if required.
 
    .DESCRIPTION
    This function checks whether the NuGet PackageProvider is already installed on the system. If it is not found and the current PowerShell version is less than 7, it attempts to install the NuGet provider using Install-PackageProvider.
    For PowerShell 7 or greater, it assumes NuGet is available by default and advises reinstallation if NuGet is missing.
 
    .PARAMETER Debug
    Enables debug output for additional details during installation.
 
    .EXAMPLE
    Install-NuGetIfRequired
    # Checks for the NuGet provider and installs it if necessary.
 
    .NOTES
    This function only attempts to install NuGet if the PowerShell version is less than 7.
    For PowerShell 7 or greater, NuGet is typically included by default and does not require installation.
    #>

    # Check if NuGet PackageProvider is already installed, skip package provider installation if found
    if (-not (Get-PackageProvider -Name NuGet -ListAvailable -ErrorAction SilentlyContinue)) {
        Write-Debug "NuGet PackageProvider not found."

        # Check if running in PowerShell version less than 7
        if ($PSVersionTable.PSVersion.Major -lt 7) {
            # Install NuGet PackageProvider if running PowerShell version less than 7
            # PowerShell 7 has limited support for installing package providers, but NuGet is available by default in PowerShell 7 so installation is not required

            Write-Debug "Installing NuGet PackageProvider..."

            if ($Debug) {
                try { Install-PackageProvider -Name "NuGet" -Force -ForceBootstrap -ErrorAction SilentlyContinue } catch { }
            } else {
                try { Install-PackageProvider -Name "NuGet" -Force -ForceBootstrap -ErrorAction SilentlyContinue | Out-Null } catch {}
            }
        } else {
            # NuGet should be included by default in PowerShell 7, so if it's not detected, advise reinstallation
            Write-Warning "NuGet is not detected in PowerShell 7. Consider reinstalling PowerShell 7, as NuGet should be included by default."
        }
    } else {
        # NuGet PackageProvider is already installed
        Write-Debug "NuGet PackageProvider is already installed. Skipping installation."
    }
}

# ============================================================================ #
# Initial checks
# ============================================================================ #

# Use global variables if specified by user
Import-GlobalVariable -VariableName "Debug"
Import-GlobalVariable -VariableName "ForceClose"
Import-GlobalVariable -VariableName "Force"

# First heading
Write-Output "winget-install $CurrentVersion"

# Check for updates if -CheckForUpdate is specified
if ($CheckForUpdate) { CheckForUpdate -RepoOwner $RepoOwner -RepoName $RepoName -CurrentVersion $CurrentVersion -PowerShellGalleryName $PowerShellGalleryName }

# Update the script if -UpdateSelf is specified
if ($UpdateSelf) { UpdateSelf }

# Heading
Write-Output "To check for updates, run winget-install -CheckForUpdate"
Write-Output "To delay script exit, run winget-install -Wait"
Write-Output "To force script pausing after execution, run winget-install -NoExit"

# Check if the current user is an administrator
if (-not (Test-AdminPrivileges)) {
    Write-Warning "winget requires Administrator privileges to install. Please run the script as an Administrator and try again."
    ExitWithDelay 1
}

# Get OS version
$osVersion = Get-OSInfo

# Get architecture type
$arch = $osVersion.Architecture

# Get current process module name to determine if launched in conhost
$currentProcess = Get-CurrentProcess

# If it's a workstation, make sure it is Windows 10+
if ($osVersion.Type -eq "Workstation" -and $osVersion.NumericVersion -lt 10) {
    Write-Error "winget requires Windows 10 or later on workstations. Your version of Windows is not supported."
    ExitWithDelay 1
}

# If it's a workstation with Windows 10, make sure it's version 1809 or greater
if ($osVersion.Type -eq "Workstation" -and $osVersion.NumericVersion -eq 10 -and $osVersion.ReleaseId -lt 1809) {
    Write-Error "winget requires Windows 10 version 1809 or later on workstations. Please update Windows to a compatible version."
    ExitWithDelay 1
}

# If it's a server, it needs to be 2019+
if ($osVersion.Type -eq "Server" -and $osVersion.NumericVersion -lt 2019) {
    Write-Error "winget requires Windows Server 2019 or newer on server platforms. Your version of Windows Server is not supported."
    ExitWithDelay 1
}

# Check if winget is already installed
if (Get-WingetStatus) {
    if ($Force -eq $false) {
        Write-Warning "winget is already installed, exiting..."
        Write-Warning "If you want to reinstall winget, run the script with the -Force parameter."
        ExitWithDelay 0 5
    }
}

# Check if ForceClose parameter is specified. If terminal detected, so relaunch in conhost
if ($ForceClose) {
    Write-Warning "ForceClose parameter is specified."
    if ($currentProcess.Name -eq "WindowsTerminal") {
        Write-Warning "Terminal detected, relaunching in conhost in 10 seconds..."
        Write-Warning "It may break your custom batch files and ps1 scripts with extra commands!"
        Start-Sleep -Seconds 10

        # Prepare the command to relaunch
        $command = "cd '$pwd'; $($MyInvocation.Line)"

        # Append parameters if their corresponding variables are $true and not already in the command
        if ($Force -and !($command -imatch '\s-Force\b')) { $command += " -Force" }
        if ($ForceClose -and !($command -imatch '\s-ForceClose\b')) { $command += " -ForceClose" }
        if ($Debug -and !($command -imatch '\s-Debug\b')) { $command += " -Debug" }

        # Relaunch in conhost
        if ([Environment]::Is64BitOperatingSystem) {
            if ([Environment]::Is64BitProcess) {
                Start-Process -FilePath "conhost.exe" -ArgumentList "powershell -ExecutionPolicy Bypass -Command &{$command}" -Verb RunAs
            } else {
                Start-Process -FilePath "$env:windir\sysnative\conhost.exe" -ArgumentList "powershell -ExecutionPolicy Bypass -Command &{$command}" -Verb RunAs
            }
        } else {
            Start-Process -FilePath "conhost.exe" -ArgumentList "powershell -ExecutionPolicy Bypass -Command &{$command}" -Verb RunAs
        }

        # Stop the current process module
        Stop-Process -id $currentProcess.Id
    } else {
        Write-Warning "Windows Terminal not detected, continuing..."
    }
}

# ============================================================================ #
# Beginning of installation process
# ============================================================================ #

try {
    # ============================================================================ #
    # winget
    # ============================================================================ #

    if ($osVersion.NumericVersion -ne 2019) {

        Write-Section "winget"

        try {
            Write-Debug "Checking if NuGet PackageProvider is already installed..."
            Install-NuGetIfRequired

            Write-Output "Installing Microsoft.WinGet.Client module..."
            if ($Debug) {
                try { Install-Module -Name Microsoft.WinGet.Client -Force -AllowClobber -Repository PSGallery -ErrorAction SilentlyContinue } catch { }
            } else {
                try { Install-Module -Name Microsoft.WinGet.Client -Force -AllowClobber -Repository PSGallery -ErrorAction SilentlyContinue *>&1 | Out-Null } catch { }
            }

            Write-Output "Installing winget (this takes a minute or two)..."
            if ($Debug) {
                try { Repair-WinGetPackageManager -AllUsers } catch { }
            } else {
                try { Repair-WinGetPackageManager -AllUsers *>&1 | Out-Null } catch { }
            }
        } catch {
            $errorHandled = Handle-Error $_
            if ($null -ne $errorHandled) {
                throw $errorHandled
            }
            $errorHandled = $null
        }

    }

    # ============================================================================ #
    # Server 2019 only
    # ============================================================================ #

    if ($osVersion.Type -eq "Server" -and $osVersion.NumericVersion -eq 2019) {

        # ============================================================================ #
        # Install prerequisites
        # ============================================================================ #

        Write-Section "Prerequisites"

        try {
            # Download VCLibs
            $VCLibs_Url = "https://aka.ms/Microsoft.VCLibs.${arch}.14.00.Desktop.appx"
            $VCLibs_Path = New-TemporaryFile2
            Write-Output "Downloading VCLibs..."
            Write-Debug "Downloading VCLibs from $VCLibs_Url to $VCLibs_Path`n`n"
            Invoke-WebRequest -Uri $VCLibs_Url -OutFile $VCLibs_Path

            # Download UI.Xaml
            $UIXaml_Url = "https://github.com/microsoft/microsoft-ui-xaml/releases/download/v2.8.6/Microsoft.UI.Xaml.2.8.${arch}.appx"
            $UIXaml_Path = New-TemporaryFile2
            Write-Output "Downloading UI.Xaml..."
            Write-Debug "Downloading UI.Xaml from $UIXaml_Url to $UIXaml_Path"
            Invoke-WebRequest -Uri $UIXaml_Url -OutFile $UIXaml_Path
        } catch {
            $errorHandled = Handle-Error $_
            if ($null -ne $errorHandled) {
                throw $errorHandled
            }
            $errorHandled = $null
        }

        # ============================================================================ #
        # winget
        # ============================================================================ #

        Write-Section "winget"

        try {

            # Download winget license
            $winget_license_path = New-TemporaryFile2
            $winget_license_url = Get-WingetDownloadUrl -Match "License1.xml"
            Write-Output "Downloading winget license..."
            Write-Debug "Downloading winget license from $winget_license_url to $winget_license_path`n`n"
            Invoke-WebRequest -Uri $winget_license_url -OutFile $winget_license_path

            # Download winget
            $winget_path = New-TemporaryFile2
            $winget_url = "https://aka.ms/getwinget"
            Write-Output "Downloading winget..."
            Write-Debug "Downloading winget from $winget_url to $winget_path`n`n"
            Invoke-WebRequest -Uri $winget_url -OutFile $winget_path

            # Install everything
            Write-Output "Installing winget and its dependencies..."
            Add-AppxProvisionedPackage -Online -PackagePath $winget_path -DependencyPackagePath $UIXaml_Path, $VCLibs_Path -LicensePath $winget_license_path | Out-Null

            # Remove temporary files
            Write-Debug "Removing temporary files..."
            TryRemove $VCLibs_Path
            TryRemove $UIXaml_Path
            TryRemove $winget_path
            TryRemove $winget_license_path
        } catch {
            $errorHandled = Handle-Error $_
            if ($null -ne $errorHandled) {
                throw $errorHandled
            }
            $errorHandled = $null
        }

        # ============================================================================ #
        # Visual C++ Redistributable
        # ============================================================================ #

        Write-Section "Visual C++ Redistributable (Server 2019 only)"

        # Test if Visual C++ Redistributable is not installed
        if (!(Test-VCRedistInstalled)) {
            Write-Debug "Visual C++ Redistributable is not installed.`n`n"

            # Define the URL and temporary file path for the download
            $VCppRedistributable_Url = "https://aka.ms/vs/17/release/vc_redist.${arch}.exe"
            $VCppRedistributable_Path = New-TemporaryFile2
            Write-Output "Downloading Visual C++ Redistributable..."
            Write-Debug "Downloading Visual C++ Redistributable from $VCppRedistributable_Url to $VCppRedistributable_Path`n`n"
            Invoke-WebRequest -Uri $VCppRedistributable_Url -OutFile $VCppRedistributable_Path

            # Rename file
            $VCppRedistributableExe_Path = $VCppRedistributable_Path + ".exe"
            Rename-Item -Path $VCppRedistributable_Path -NewName $VCppRedistributableExe_Path

            # Install Visual C++ Redistributable
            Write-Output "Installing Visual C++ Redistributable..."
            Write-Debug "Installing Visual C++ Redistributable from $VCppRedistributableExe_Path`n`n"
            Start-Process -FilePath $VCppRedistributableExe_Path -ArgumentList "/install", "/quiet", "/norestart" -Wait

            Write-Debug "Removing temporary file..."
            TryRemove $VCppRedistributableExe_Path
        } else {
            Write-Output "Visual C++ Redistributable is already installed."
        }

        # ============================================================================ #
        # Fix environment PATH and permissions
        # ============================================================================ #

        # Fix permissions for winget folder (Server 2019 only)
        Write-Output "Fixing permissions for winget folder (Server 2019 only)..."

        # Find winget folder path in Program Files
        $WinGetFolderPath = (Get-ChildItem -Path ([System.IO.Path]::Combine($env:ProgramFiles, 'WindowsApps')) -Filter "Microsoft.DesktopAppInstaller_*_${arch}__8wekyb3d8bbwe" | Sort-Object Name | Select-Object -Last 1).FullName
        Write-Debug "WinGetFolderPath: $WinGetFolderPath"

        if ($null -ne $WinGetFolderPath) {
            # Fix Permissions by adding Administrators group with FullControl
            Set-PathPermissions -FolderPath $WinGetFolderPath

            # Add Environment Path
            Add-ToEnvironmentPath -PathToAdd $WinGetFolderPath -Scope 'System'
        } else {
            Write-Warning "winget folder path not found. You may need to manually add winget's folder path to your system PATH environment variable."
        }
    }

    # ============================================================================ #
    # Done
    # ============================================================================ #

    Write-Section "Complete"

    Write-Output "winget installed successfully."

    # ============================================================================ #
    # Finished
    # ============================================================================ #

    # Timeout before checking winget
    Write-Output "Checking if winget is installed and working..."
    Start-Sleep -Seconds 3

    # Check if winget is installed
    if (Get-WingetStatus -eq $true) {
        Write-Output "winget is installed and working. You can go ahead and use it."
    } else {
        # If winget is still not detected as a command, show warning
        if (Get-WingetStatus -eq $false) {
            Write-Warning "winget is installed but is not detected as a command. Try using winget now. If it doesn't work, wait about 1 minute and try again (it is sometimes delayed). Also try restarting your computer."
            Write-Warning "If you restart your computer and the command still isn't recognized, please read the Troubleshooting section`nof the README: https://github.com/asheroto/winget-install#troubleshooting`n"
            Write-Warning "Make sure you have the latest version of the script by running this command: $PowerShellGalleryName -CheckForUpdate`n`n"
        }
    }

    ExitWithDelay 0
} catch {
    # ============================================================================ #
    # Error handling
    # ============================================================================ #

    Write-Section "WARNING! An error occurred during installation!"
    Write-Warning "If messages above don't help and the problem persists, please read the Troubleshooting section`nof the README: https://github.com/asheroto/winget-install#troubleshooting"
    Write-Warning "Make sure you have the latest version of the script by running this command: $PowerShellGalleryName -CheckForUpdate`n`n"

    # If it's not 0x80073D02 (resources in use), show error
    if ($_.Exception.Message -notmatch '0x80073D02') {
        if ($Debug) {
            Write-Warning "Line number : $($_.InvocationInfo.ScriptLineNumber)"
        }
        Write-Warning "Error: $($_.Exception.Message)`n"
    }

    ExitWithDelay 1
}
# SIG # Begin signature block
# MIIhEwYJKoZIhvcNAQcCoIIhBDCCIQACAQExDzANBglghkgBZQMEAgIFADCBiQYK
# KwYBBAGCNwIBBKB7MHkwNAYKKwYBBAGCNwIBHjAmAgMBAAAEEB/MO2BZSwhOtyTS
# xil+81ECAQACAQACAQACAQACAQAwQTANBglghkgBZQMEAgIFAAQwg/BlQ3sSHk4i
# LeXVVbRoar9+jv+hETLHBk6AgdRO+gEexgSRZ1FS3GMlAdVbJFa2oIIHZDCCA1kw
# ggLfoAMCAQICEA+4p0C5FY0DUUO8WdnwQCkwCgYIKoZIzj0EAwMwYTELMAkGA1UE
# BhMCVVMxFTATBgNVBAoTDERpZ2lDZXJ0IEluYzEZMBcGA1UECxMQd3d3LmRpZ2lj
# ZXJ0LmNvbTEgMB4GA1UEAxMXRGlnaUNlcnQgR2xvYmFsIFJvb3QgRzMwHhcNMjEw
# NDI5MDAwMDAwWhcNMzYwNDI4MjM1OTU5WjBkMQswCQYDVQQGEwJVUzEXMBUGA1UE
# ChMORGlnaUNlcnQsIEluYy4xPDA6BgNVBAMTM0RpZ2lDZXJ0IEdsb2JhbCBHMyBD
# b2RlIFNpZ25pbmcgRUNDIFNIQTM4NCAyMDIxIENBMTB2MBAGByqGSM49AgEGBSuB
# BAAiA2IABLu0rCelSA2iU1+PLoE+L1N2uAiUopqqiouYtbHw/CoVu7mzpSIv/WrA
# veJVaGBrlzTBZlNxI/wa1cogDwJAoqNKWkajkVMrlfID6aum04d2L+dkn541UfzD
# YzV4duT4d6OCAVcwggFTMBIGA1UdEwEB/wQIMAYBAf8CAQAwHQYDVR0OBBYEFJtf
# sDa6nQauGSe9wKAiwIuLOHftMB8GA1UdIwQYMBaAFLPbSKT5ocXYrjZBzBFjaWIp
# vEvGMA4GA1UdDwEB/wQEAwIBhjATBgNVHSUEDDAKBggrBgEFBQcDAzB2BggrBgEF
# BQcBAQRqMGgwJAYIKwYBBQUHMAGGGGh0dHA6Ly9vY3NwLmRpZ2ljZXJ0LmNvbTBA
# BggrBgEFBQcwAoY0aHR0cDovL2NhY2VydHMuZGlnaWNlcnQuY29tL0RpZ2lDZXJ0
# R2xvYmFsUm9vdEczLmNydDBCBgNVHR8EOzA5MDegNaAzhjFodHRwOi8vY3JsMy5k
# aWdpY2VydC5jb20vRGlnaUNlcnRHbG9iYWxSb290RzMuY3JsMBwGA1UdIAQVMBMw
# BwYFZ4EMAQMwCAYGZ4EMAQQBMAoGCCqGSM49BAMDA2gAMGUCMHi9SZVlcQHQRldo
# ZQ5oqdw2CMHu/dSO20BlPw3/k6/CrmOGo37LtJFaeOwHA2cHfAIxAOefH/EHW6w0
# xji8taVQzubqOH4+eZDkpFurAg3oB/xWplqK3bNQst3y+mZ0ntAWYzCCBAMwggOJ
# oAMCAQICEAExw+sKUABDj0yZt5afTZQwCgYIKoZIzj0EAwMwZDELMAkGA1UEBhMC
# VVMxFzAVBgNVBAoTDkRpZ2lDZXJ0LCBJbmMuMTwwOgYDVQQDEzNEaWdpQ2VydCBH
# bG9iYWwgRzMgQ29kZSBTaWduaW5nIEVDQyBTSEEzODQgMjAyMSBDQTEwHhcNMjQw
# MzA3MDAwMDAwWhcNMjUwMzA4MjM1OTU5WjBvMQswCQYDVQQGEwJVUzERMA8GA1UE
# CBMIT2tsYWhvbWExETAPBgNVBAcTCE11c2tvZ2VlMRwwGgYDVQQKExNBc2hlciBT
# b2x1dGlvbnMgSW5jMRwwGgYDVQQDExNBc2hlciBTb2x1dGlvbnMgSW5jMHYwEAYH
# KoZIzj0CAQYFK4EEACIDYgAExsP0nyCZ1QtY7aXin+tdZVcF0uPHJJjRpjVVgUmb
# 3iKJeKapvWBSAbroBouKIP9+Qoz197aNbZCSOBQsWX53SUyTu1Trvwku7ksL+eQh
# bJvnRJ20UqF566z5KbniyLrAo4IB8zCCAe8wHwYDVR0jBBgwFoAUm1+wNrqdBq4Z
# J73AoCLAi4s4d+0wHQYDVR0OBBYEFNdgDYHKEBunNDYgivfxKeS4YX0/MD4GA1Ud
# IAQ3MDUwMwYGZ4EMAQQBMCkwJwYIKwYBBQUHAgEWG2h0dHA6Ly93d3cuZGlnaWNl
# cnQuY29tL0NQUzAOBgNVHQ8BAf8EBAMCB4AwEwYDVR0lBAwwCgYIKwYBBQUHAwMw
# gasGA1UdHwSBozCBoDBOoEygSoZIaHR0cDovL2NybDMuZGlnaWNlcnQuY29tL0Rp
# Z2lDZXJ0R2xvYmFsRzNDb2RlU2lnbmluZ0VDQ1NIQTM4NDIwMjFDQTEuY3JsME6g
# TKBKhkhodHRwOi8vY3JsNC5kaWdpY2VydC5jb20vRGlnaUNlcnRHbG9iYWxHM0Nv
# ZGVTaWduaW5nRUNDU0hBMzg0MjAyMUNBMS5jcmwwgY4GCCsGAQUFBwEBBIGBMH8w
# JAYIKwYBBQUHMAGGGGh0dHA6Ly9vY3NwLmRpZ2ljZXJ0LmNvbTBXBggrBgEFBQcw
# AoZLaHR0cDovL2NhY2VydHMuZGlnaWNlcnQuY29tL0RpZ2lDZXJ0R2xvYmFsRzND
# b2RlU2lnbmluZ0VDQ1NIQTM4NDIwMjFDQTEuY3J0MAkGA1UdEwQCMAAwCgYIKoZI
# zj0EAwMDaAAwZQIxAJHtFqbIBTSZ6AiYEyHsjjlZ7treTZfTSPiyyr8KAKBPKVXt
# B2859Jj8A3c9lEXrLgIwGTu2YV8DhFy9OqIDwkCZfoYH8oMo1LRtYhYZtVzkr3WF
# er8mkmAdOyNbW/DI0pZPMYIY9DCCGPACAQEweDBkMQswCQYDVQQGEwJVUzEXMBUG
# A1UEChMORGlnaUNlcnQsIEluYy4xPDA6BgNVBAMTM0RpZ2lDZXJ0IEdsb2JhbCBH
# MyBDb2RlIFNpZ25pbmcgRUNDIFNIQTM4NCAyMDIxIENBMQIQATHD6wpQAEOPTJm3
# lp9NlDANBglghkgBZQMEAgIFAKCBjDAQBgorBgEEAYI3AgEMMQIwADAZBgkqhkiG
# 9w0BCQMxDAYKKwYBBAGCNwIBBDAcBgorBgEEAYI3AgELMQ4wDAYKKwYBBAGCNwIB
# FTA/BgkqhkiG9w0BCQQxMgQwQyHvFvJRsvzAD9Qh6qY4+k2VQddYH8qM5a01rrSP
# 7VOmN7aD5d0+7bHjBNKXzy3vMAsGByqGSM49AgEFAARoMGYCMQCB/NtRb6vSCsmO
# XDy7iVKhUmkiUf2Kkn2OdgHYY5YfTFxbIOi8xG61du6AaSWkf1kCMQCt5aNKecSh
# cNTIzwYkW3DaHdYhzzZFURnQoqHCmsSqeqV1+05GNQu3fn+AQgSTwRuhghdaMIIX
# VgYKKwYBBAGCNwMDATGCF0YwghdCBgkqhkiG9w0BBwKgghczMIIXLwIBAzEPMA0G
# CWCGSAFlAwQCAgUAMIGHBgsqhkiG9w0BCRABBKB4BHYwdAIBAQYJYIZIAYb9bAcB
# MEEwDQYJYIZIAWUDBAICBQAEMMveAtn+hqWl/atrw4zwtlor733veMIH7UwXbVAG
# 6kDyOHDpGoFteuund0BGemOkagIQRkfJ97tYTmQCV2NUXoqxrxgPMjAyNDExMTgy
# MTA2NDlaoIITAzCCBrwwggSkoAMCAQICEAuuZrxaun+Vh8b56QTjMwQwDQYJKoZI
# hvcNAQELBQAwYzELMAkGA1UEBhMCVVMxFzAVBgNVBAoTDkRpZ2lDZXJ0LCBJbmMu
# MTswOQYDVQQDEzJEaWdpQ2VydCBUcnVzdGVkIEc0IFJTQTQwOTYgU0hBMjU2IFRp
# bWVTdGFtcGluZyBDQTAeFw0yNDA5MjYwMDAwMDBaFw0zNTExMjUyMzU5NTlaMEIx
# CzAJBgNVBAYTAlVTMREwDwYDVQQKEwhEaWdpQ2VydDEgMB4GA1UEAxMXRGlnaUNl
# cnQgVGltZXN0YW1wIDIwMjQwggIiMA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoIC
# AQC+anOf9pUhq5Ywultt5lmjtej9kR8YxIg7apnjpcH9CjAgQxK+CMR0Rne/i+ut
# MeV5bUlYYSuuM4vQngvQepVHVzNLO9RDnEXvPghCaft0djvKKO+hDu6ObS7rJcXa
# /UKvNminKQPTv/1+kBPgHGlP28mgmoCw/xi6FG9+Un1h4eN6zh926SxMe6We2r1Z
# 6VFZj75MU/HNmtsgtFjKfITLutLWUdAoWle+jYZ49+wxGE1/UXjWfISDmHuI5e/6
# +NfQrxGFSKx+rDdNMsePW6FLrphfYtk/FLihp/feun0eV+pIF496OVh4R1TvjQYp
# AztJpVIfdNsEvxHofBf1BWkadc+Up0Th8EifkEEWdX4rA/FE1Q0rqViTbLVZIqi6
# viEk3RIySho1XyHLIAOJfXG5PEppc3XYeBH7xa6VTZ3rOHNeiYnY+V4j1XbJ+Z9d
# I8ZhqcaDHOoj5KGg4YuiYx3eYm33aebsyF6eD9MF5IDbPgjvwmnAalNEeJPvIeoG
# JXaeBQjIK13SlnzODdLtuThALhGtyconcVuPI8AaiCaiJnfdzUcb3dWnqUnjXkRF
# wLtsVAxFvGqsxUA2Jq/WTjbnNjIUzIs3ITVC6VBKAOlb2u29Vwgfta8b2ypi6n2P
# zP0nVepsFk8nlcuWfyZLzBaZ0MucEdeBiXL+nUOGhCjl+QIDAQABo4IBizCCAYcw
# DgYDVR0PAQH/BAQDAgeAMAwGA1UdEwEB/wQCMAAwFgYDVR0lAQH/BAwwCgYIKwYB
# BQUHAwgwIAYDVR0gBBkwFzAIBgZngQwBBAIwCwYJYIZIAYb9bAcBMB8GA1UdIwQY
# MBaAFLoW2W1NhS9zKXaaL3WMaiCPnshvMB0GA1UdDgQWBBSfVywDdw4oFZBmpWNe
# 7k+SH3agWzBaBgNVHR8EUzBRME+gTaBLhklodHRwOi8vY3JsMy5kaWdpY2VydC5j
# b20vRGlnaUNlcnRUcnVzdGVkRzRSU0E0MDk2U0hBMjU2VGltZVN0YW1waW5nQ0Eu
# Y3JsMIGQBggrBgEFBQcBAQSBgzCBgDAkBggrBgEFBQcwAYYYaHR0cDovL29jc3Au
# ZGlnaWNlcnQuY29tMFgGCCsGAQUFBzAChkxodHRwOi8vY2FjZXJ0cy5kaWdpY2Vy
# dC5jb20vRGlnaUNlcnRUcnVzdGVkRzRSU0E0MDk2U0hBMjU2VGltZVN0YW1waW5n
# Q0EuY3J0MA0GCSqGSIb3DQEBCwUAA4ICAQA9rR4fdplb4ziEEkfZQ5H2EdubTggd
# 0ShPz9Pce4FLJl6reNKLkZd5Y/vEIqFWKt4oKcKz7wZmXa5VgW9B76k9NJxUl4Jl
# KwyjUkKhk3aYx7D8vi2mpU1tKlY71AYXB8wTLrQeh83pXnWwwsxc1Mt+FWqz57yF
# q6laICtKjPICYYf/qgxACHTvypGHrC8k1TqCeHk6u4I/VBQC9VK7iSpU5wlWjNlH
# lFFv/M93748YTeoXU/fFa9hWJQkuzG2+B7+bMDvmgF8VlJt1qQcl7YFUMYgZU1WM
# 6nyw23vT6QSgwX5Pq2m0xQ2V6FJHu8z4LXe/371k5QrN9FQBhLLISZi2yemW0P8Z
# Zfx4zvSWzVXpAb9k4Hpvpi6bUe8iK6WonUSV6yPlMwerwJZP/Gtbu3CKldMnn+Lm
# mRTkTXpFIEB06nXZrDwhCGED+8RsWQSIXZpuG4WLFQOhtloDRWGoCwwc6ZpPddOF
# kM2LlTbMcqFSzm4cd0boGhBq7vkqI1uHRz6Fq1IX7TaRQuR+0BGOzISkcqwXu7nM
# pFu3mgrlgbAW+BzikRVQ3K2YHcGkiKjA4gi4OA/kz1YCsdhIBHXqBzR0/Zd2QwQ/
# l4Gxftt/8wY3grcc/nS//TVkej9nmUYu83BDtccHHXKibMs/yXHhDXNkoPIdynhV
# Aku7aRZOwqw6pDCCBq4wggSWoAMCAQICEAc2N7ckVHzYR6z9KGYqXlswDQYJKoZI
# hvcNAQELBQAwYjELMAkGA1UEBhMCVVMxFTATBgNVBAoTDERpZ2lDZXJ0IEluYzEZ
# MBcGA1UECxMQd3d3LmRpZ2ljZXJ0LmNvbTEhMB8GA1UEAxMYRGlnaUNlcnQgVHJ1
# c3RlZCBSb290IEc0MB4XDTIyMDMyMzAwMDAwMFoXDTM3MDMyMjIzNTk1OVowYzEL
# MAkGA1UEBhMCVVMxFzAVBgNVBAoTDkRpZ2lDZXJ0LCBJbmMuMTswOQYDVQQDEzJE
# aWdpQ2VydCBUcnVzdGVkIEc0IFJTQTQwOTYgU0hBMjU2IFRpbWVTdGFtcGluZyBD
# QTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAMaGNQZJs8E9cklRVccl
# A8TykTepl1Gh1tKD0Z5Mom2gsMyD+Vr2EaFEFUJfpIjzaPp985yJC3+dH54PMx9Q
# Ewsmc5Zt+FeoAn39Q7SE2hHxc7Gz7iuAhIoiGN/r2j3EF3+rGSs+QtxnjupRPfDW
# VtTnKC3r07G1decfBmWNlCnT2exp39mQh0YAe9tEQYncfGpXevA3eZ9drMvohGS0
# UvJ2R/dhgxndX7RUCyFobjchu0CsX7LeSn3O9TkSZ+8OpWNs5KbFHc02DVzV5huo
# wWR0QKfAcsW6Th+xtVhNef7Xj3OTrCw54qVI1vCwMROpVymWJy71h6aPTnYVVSZw
# mCZ/oBpHIEPjQ2OAe3VuJyWQmDo4EbP29p7mO1vsgd4iFNmCKseSv6De4z6ic/rn
# H1pslPJSlRErWHRAKKtzQ87fSqEcazjFKfPKqpZzQmiftkaznTqj1QPgv/CiPMpC
# 3BhIfxQ0z9JMq++bPf4OuGQq+nUoJEHtQr8FnGZJUlD0UfM2SU2LINIsVzV5K6jz
# RWC8I41Y99xh3pP+OcD5sjClTNfpmEpYPtMDiP6zj9NeS3YSUZPJjAw7W4oiqMEm
# CPkUEBIDfV8ju2TjY+Cm4T72wnSyPx4JduyrXUZ14mCjWAkBKAAOhFTuzuldyF4w
# Er1GnrXTdrnSDmuZDNIztM2xAgMBAAGjggFdMIIBWTASBgNVHRMBAf8ECDAGAQH/
# AgEAMB0GA1UdDgQWBBS6FtltTYUvcyl2mi91jGogj57IbzAfBgNVHSMEGDAWgBTs
# 1+OC0nFdZEzfLmc/57qYrhwPTzAOBgNVHQ8BAf8EBAMCAYYwEwYDVR0lBAwwCgYI
# KwYBBQUHAwgwdwYIKwYBBQUHAQEEazBpMCQGCCsGAQUFBzABhhhodHRwOi8vb2Nz
# cC5kaWdpY2VydC5jb20wQQYIKwYBBQUHMAKGNWh0dHA6Ly9jYWNlcnRzLmRpZ2lj
# ZXJ0LmNvbS9EaWdpQ2VydFRydXN0ZWRSb290RzQuY3J0MEMGA1UdHwQ8MDowOKA2
# oDSGMmh0dHA6Ly9jcmwzLmRpZ2ljZXJ0LmNvbS9EaWdpQ2VydFRydXN0ZWRSb290
# RzQuY3JsMCAGA1UdIAQZMBcwCAYGZ4EMAQQCMAsGCWCGSAGG/WwHATANBgkqhkiG
# 9w0BAQsFAAOCAgEAfVmOwJO2b5ipRCIBfmbW2CFC4bAYLhBNE88wU86/GPvHUF3i
# Syn7cIoNqilp/GnBzx0H6T5gyNgL5Vxb122H+oQgJTQxZ822EpZvxFBMYh0MCIKo
# Fr2pVs8Vc40BIiXOlWk/R3f7cnQU1/+rT4osequFzUNf7WC2qk+RZp4snuCKrOX9
# jLxkJodskr2dfNBwCnzvqLx1T7pa96kQsl3p/yhUifDVinF2ZdrM8HKjI/rAJ4JE
# rpknG6skHibBt94q6/aesXmZgaNWhqsKRcnfxI2g55j7+6adcq/Ex8HBanHZxhOA
# CcS2n82HhyS7T6NJuXdmkfFynOlLAlKnN36TU6w7HQhJD5TNOXrd/yVjmScsPT9r
# p/Fmw0HNT7ZAmyEhQNC3EyTN3B14OuSereU0cZLXJmvkOHOrpgFPvT87eK1MrfvE
# lXvtCl8zOYdBeHo46Zzh3SP9HSjTx/no8Zhf+yvYfvJGnXUsHicsJttvFXseGYs2
# uJPU5vIXmVnKcPA3v5gA3yAWTyf7YGcWoWa63VXAOimGsJigK+2VQbc61RWYMbRi
# CQ8KvYHZE/6/pNHzV9m8BPqC3jLfBInwAM1dwvnQI38AC+R2AibZ8GV2QqYphwlH
# K+Z/GqSFD/yYlvZVVCsfgPrA8g4r5db7qS9EFUrnEw4d2zc4GqEr9u3WfPwwggWN
# MIIEdaADAgECAhAOmxiO+dAt5+/bUOIIQBhaMA0GCSqGSIb3DQEBDAUAMGUxCzAJ
# BgNVBAYTAlVTMRUwEwYDVQQKEwxEaWdpQ2VydCBJbmMxGTAXBgNVBAsTEHd3dy5k
# aWdpY2VydC5jb20xJDAiBgNVBAMTG0RpZ2lDZXJ0IEFzc3VyZWQgSUQgUm9vdCBD
# QTAeFw0yMjA4MDEwMDAwMDBaFw0zMTExMDkyMzU5NTlaMGIxCzAJBgNVBAYTAlVT
# MRUwEwYDVQQKEwxEaWdpQ2VydCBJbmMxGTAXBgNVBAsTEHd3dy5kaWdpY2VydC5j
# b20xITAfBgNVBAMTGERpZ2lDZXJ0IFRydXN0ZWQgUm9vdCBHNDCCAiIwDQYJKoZI
# hvcNAQEBBQADggIPADCCAgoCggIBAL/mkHNo3rvkXUo8MCIwaTPswqclLskhPfKK
# 2FnC4SmnPVirdprNrnsbhA3EMB/zG6Q4FutWxpdtHauyefLKEdLkX9YFPFIPUh/G
# nhWlfr6fqVcWWVVyr2iTcMKyunWZanMylNEQRBAu34LzB4TmdDttceItDBvuINXJ
# IB1jKS3O7F5OyJP4IWGbNOsFxl7sWxq868nPzaw0QF+xembud8hIqGZXV59UWI4M
# K7dPpzDZVu7Ke13jrclPXuU15zHL2pNe3I6PgNq2kZhAkHnDeMe2scS1ahg4AxCN
# 2NQ3pC4FfYj1gj4QkXCrVYJBMtfbBHMqbpEBfCFM1LyuGwN1XXhm2ToxRJozQL8I
# 11pJpMLmqaBn3aQnvKFPObURWBf3JFxGj2T3wWmIdph2PVldQnaHiZdpekjw4KIS
# G2aadMreSx7nDmOu5tTvkpI6nj3cAORFJYm2mkQZK37AlLTSYW3rM9nF30sEAMx9
# HJXDj/chsrIRt7t/8tWMcCxBYKqxYxhElRp2Yn72gLD76GSmM9GJB+G9t+ZDpBi4
# pncB4Q+UDCEdslQpJYls5Q5SUUd0viastkF13nqsX40/ybzTQRESW+UQUOsxxcpy
# FiIJ33xMdT9j7CFfxCBRa2+xq4aLT8LWRV+dIPyhHsXAj6KxfgommfXkaS+YHS31
# 2amyHeUbAgMBAAGjggE6MIIBNjAPBgNVHRMBAf8EBTADAQH/MB0GA1UdDgQWBBTs
# 1+OC0nFdZEzfLmc/57qYrhwPTzAfBgNVHSMEGDAWgBRF66Kv9JLLgjEtUYunpyGd
# 823IDzAOBgNVHQ8BAf8EBAMCAYYweQYIKwYBBQUHAQEEbTBrMCQGCCsGAQUFBzAB
# hhhodHRwOi8vb2NzcC5kaWdpY2VydC5jb20wQwYIKwYBBQUHMAKGN2h0dHA6Ly9j
# YWNlcnRzLmRpZ2ljZXJ0LmNvbS9EaWdpQ2VydEFzc3VyZWRJRFJvb3RDQS5jcnQw
# RQYDVR0fBD4wPDA6oDigNoY0aHR0cDovL2NybDMuZGlnaWNlcnQuY29tL0RpZ2lD
# ZXJ0QXNzdXJlZElEUm9vdENBLmNybDARBgNVHSAECjAIMAYGBFUdIAAwDQYJKoZI
# hvcNAQEMBQADggEBAHCgv0NcVec4X6CjdBs9thbX979XB72arKGHLOyFXqkauyL4
# hxppVCLtpIh3bb0aFPQTSnovLbc47/T/gLn4offyct4kvFIDyE7QKt76LVbP+fT3
# rDB6mouyXtTP0UNEm0Mh65ZyoUi0mcudT6cGAxN3J0TU53/oWajwvy8LpunyNDzs
# 9wPHh6jSTEAZNUZqaVSwuKFWjuyk1T3osdz9HNj0d1pcVIxv76FQPfx2CWiEn2/K
# 2yCNNWAcAgPLILCsWKAOQGPFmCLBsln1VWvPJ6tsds5vIy30fnFqI2si/xK4VC0n
# ftg62fC2h5b9W9FcrBjDTZ9ztwGpn1eqXijiuZQxggOGMIIDggIBATB3MGMxCzAJ
# BgNVBAYTAlVTMRcwFQYDVQQKEw5EaWdpQ2VydCwgSW5jLjE7MDkGA1UEAxMyRGln
# aUNlcnQgVHJ1c3RlZCBHNCBSU0E0MDk2IFNIQTI1NiBUaW1lU3RhbXBpbmcgQ0EC
# EAuuZrxaun+Vh8b56QTjMwQwDQYJYIZIAWUDBAICBQCggeEwGgYJKoZIhvcNAQkD
# MQ0GCyqGSIb3DQEJEAEEMBwGCSqGSIb3DQEJBTEPFw0yNDExMTgyMTA2NDlaMCsG
# CyqGSIb3DQEJEAIMMRwwGjAYMBYEFNvThe5i29I+e+T2cUhQhyTVhltFMDcGCyqG
# SIb3DQEJEAIvMSgwJjAkMCIEIHZ2n6jyYy8fQws6IzCu1lZ1/tdz2wXWZbkFk5hD
# j5rbMD8GCSqGSIb3DQEJBDEyBDCbGRL246H6zOyjIpJFB6YLvJOiJ3AFObDqA3DT
# O+eH1OuDErVUAxadWaALkPU367gwDQYJKoZIhvcNAQEBBQAEggIAAYiisHoItDtv
# 278C1nKOepIg/AJvDYRJffWQlkn3Fz3wudOdDyD5ZR6xOVJMA9EuMLpwd25qB/bL
# nmcu3dDEj8z2PJMfs04pBiALzkTfGhS8I1iGH3lgtqMCj9dyuz6Y7BfaFSKW29bT
# COffRyAHc25w1aasaK5y9IwQ1qZXwcC8zFavPGLzaFtI9c3BomrQpCRPfAfbKuvo
# C++RtgQoq5vbjj8lJ9N0xCW4x5DwJsuAkpkwqN9o8MDEog96RHpYbZmX0oBEXWT5
# c6igpcAqp6lPuXDwrwylIa+g3qAxOmnWeM1UQANJMu8Mx58aVIL9QUxOyUFuLgpy
# pZDiBgVFX7ibO3k1JEcvMyqDN1FKlxyiuysuRCfZNbdBy/aVMWQ9cuKlR09nKJ4g
# +iLFdAkAw31lOzAYGzJ6XSbwFCt1IcgkmDFESB9ahqPbVH0aIf+X/zKVmI/k4Frg
# hHSkD1ukzs9heM4KZGRSBbFddyTH8inxVqyKdMrX9sqOMgLRvfipI257akBoFTbZ
# eAF3401j0Ts+Mz+TrHkHC8obb6rs5avI0I8ggE8Wt8Cv8mbjmWKHAAxHadzLGuoG
# axuj+SXXd4XhNsBQfs5+JR1f2g9geWHmCMHMKQdue3NgHZsQ8XGO19limt55fWOK
# 3PUQ6uvwGR8Vs95Bd1rx+rZi08Z8I+E=
# SIG # End signature block