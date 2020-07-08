{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  shellHook = ''
    export LANG=en_US.UTF-8
    export LC_CTYPE="en_US.UTF-8"
    export LC_NUMERIC="en_US.UTF-8"
    export LC_TIME="en_US.UTF-8"
    export LC_COLLATE="en_US.UTF-8"
    export LC_MONETARY="en_US.UTF-8"
    export LC_MESSAGES="en_US.UTF-8"
    export LC_PAPER="en_US.UTF-8"
    export LC_NAME="en_US.UTF-8"
    export LC_ADDRESS="en_US.UTF-8"
    export LC_TELEPHONE="en_US.UTF-8"
    export LC_MEASUREMENT="en_US.UTF-8"
    export LC_IDENTIFICATION="en_US.UTF-8"
    
    echo "Compiling application.."
    sbt compile
    echo "Compilation done."
    echo "Type 'sbt run' to run the glassball app"
  '';
  buildInputs = with pkgs; [
    glibcLocales 
    curl
    jdk
    less
    sbt
    vim
  ];
}
