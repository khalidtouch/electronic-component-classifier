from selenium import webdriver
from selenium.webdriver.common.by import By
import requests
import io
from PIL import Image
import time
import os
import hashlib

DRIVER_PATH = "C:\Users\admin\Documents\Web scraping"
wd = webdriver.Chrome(executable_path= DRIVER_PATH)

def fetch_image_urls(query:str, max_links_to_fetch:int, wd:webdriver, sleep_between_interactions:int=5):
    def scroll_to_end(wd):
        wd.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        time.sleep(sleep_between_interactions)    
    
    # build the google query
    search_url = "https://www.google.com/search?q=Capacitors&tbm=isch&ved=2ahUKEwiLpMvS98_-AhUfmycCHWpGAEEQ2-cCegQIABAA&oq=Capacitors&gs_lcp=CgNpbWcQAzIHCAAQigUQQzIHCAAQigUQQzIHCAAQigUQQzIHCAAQigUQQzIFCAAQgAQyBwgAEIoFEEMyBwgAEIoFEEMyBwgAEIoFEEMyBwgAEIoFEEMyBQgAEIAEUABYAGDNGmgBcAB4AIAB1QWIAdUFkgEDNi0xmAEAqgELZ3dzLXdpei1pbWfAAQE&sclient=img&ei=r35NZMuQJJ-2nsEP6oyBiAQ&bih=696&biw=1536"

    # load the page
    wd.get(search_url.format(q=query))

    image_urls = set()
    image_count = 0
    results_start = 0
    while image_count < max_links_to_fetch:
        scroll_to_end(wd)

        # get all image thumbnail results
        thumbnail_results = wd.find_element(By.CLASS_NAME, "iPVvYb")
        number_results = len(thumbnail_results)
        
        print(f"Found: {number_results} search results. Extracting links from {results_start}:{number_results}")
        
        for img in thumbnail_results[results_start:number_results]:
            # try to click every thumbnail such that we can get the real image behind it
            try:
                img.click()
                time.sleep(sleep_between_interactions)
            except Exception:
                continue

            # extract image urls    
            actual_images = wd.find_element(By.CLASS_NAME, 'r48jcc')
            for actual_image in actual_images:
                if actual_image.get_attribute('src') and 'http' in actual_image.get_attribute('src'):
                    image_urls.add(actual_image.get_attribute('src'))

            image_count = len(image_urls)

            if len(image_urls) >= max_links_to_fetch:
                print(f"Found: {len(image_urls)} image links, done!")
                break
        else:
            print("Found:", len(image_urls), "image links, looking for more ...")
            time.sleep(30)
            return
            load_more_button = wd.find_element(By.CSS_SELECTOR, "img.rg_i.Q4LuWd")
            if load_more_button:
                wd.execute_script("document.querySelector('img.rg_i.Q4LuWd').click();")

        # move the result startpoint further down
        results_start = len(thumbnail_results)

    return image_urls


def download_image(download_path,url):
    try:
        image_content = requests.get(url).content
        image_file = io.BytesIO(image_content)
        image = Image.open(image_file)
        # file_path = download_path + filename
        file_path = os.path.join(download_path,hashlib.sha1(image_content).hexdigest()[:10] + '.jpg')
        
        with open(file_path, 'wb') as f:
            image.save(f, "JPEG")
            
        print(f"SUCCESS")
    except Exception as e:
        print(f"ERROR - Could not save {url} - {e}")

img_url='https://passive-components.eu/wp-content/uploads/2018/07/electrolytics.jpg'        
download_image('',img_url)        

# def search_and_download(search_term:str,driver_path:str,target_path='C:\Users\admin\Documents\Web scraping\images',number_images=5):
#     target_folder = os.path.join(target_path,'_'.join(search_term.lower().split(' ')))

#     if not os.path.exists(target_folder):
#         os.makedirs(target_folder)

#     with webdriver.Chrome(executable_path=driver_path) as wd:
#         res = fetch_image_urls(search_term, number_images, wd=wd, sleep_between_interactions=0.5)
        
#     for elem in res:
#         persist_image(target_folder,elem)
        
        
        
# search_term = 'Capacitor'

# search_and_download(search_term=search_term, driver_path=DRIVER_PATH)

